SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE PROCEDURE [dbo].[p_Trading_CreateTransactions]
	 --@ProcessDate           datetime

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;


	-------------------------------------------------------
	-- Type Variables
	-------------------------------------------------------
	DECLARE
			 @ORDER_TYPE_BUY		        decimal(18,0)
			,@ORDER_TYPE_SELL		        decimal(18,0)
			,@IS_USER_ORDER		        	decimal(18,0)
			,@ORDER_STATUS_OPEN	        	decimal(18,0)
			,@ORDER_STATUS_PARTIAL	        decimal(18,0)
			,@ORDER_STATUS_Filled			decimal(18,0)
			,@TRANSACTION_STATUS_PENDING	decimal(18,0)
			,@PROCESS_STATUS_PENDING		decimal(18,0)
			,@PROCESS_STATUS_RUNNING		decimal(18,0)
			,@PROCESS_STATUS_SUCCESS		decimal(18,0)
			,@PROCESS_STATUS_FAILED			decimal(18,0)
			,@SECURITY_FK					decimal(18,0)

			,@RETURN_SUCCESS				int
			,@RETURN_ERROR_NOT_READY		int
			,@RETURN_ERROR_NEEDS_UNDO		int

	SET @ORDER_TYPE_BUY  = (SELECT Type_PK FROM Order_Types WHERE Code = 'BUY')
	SET @ORDER_TYPE_SELL = (SELECT Type_PK FROM Order_Types WHERE Code = 'SELL')
	SET @ORDER_STATUS_OPEN = (SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'OPEN')
	SET @ORDER_STATUS_PARTIAL = (SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'PARTIAL')
	SET @ORDER_STATUS_Filled = (SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'FILLED')
	SET @TRANSACTION_STATUS_PENDING = (SELECT TransactionStatus_PK FROM TransactionStatus_Types WHERE TransactionStatus = 'Pending')
	SET @PROCESS_STATUS_PENDING = (SELECT ProcessStatus_PK FROM ProcessStatus_Types WHERE ProcessStatus = 'Pending')
	SET @PROCESS_STATUS_RUNNING = (SELECT ProcessStatus_PK FROM ProcessStatus_Types WHERE ProcessStatus = 'Running')
	SET @PROCESS_STATUS_SUCCESS = (SELECT ProcessStatus_PK FROM ProcessStatus_Types WHERE ProcessStatus = 'Success')
	SET @PROCESS_STATUS_FAILED = (SELECT ProcessStatus_PK FROM ProcessStatus_Types WHERE ProcessStatus = 'Failed')
	SET @SECURITY_FK = (SELECT Security_PK FROM Securities WHERE Name = 'A')

	SET @RETURN_SUCCESS						= 1
	SET @RETURN_ERROR_NOT_READY				= 2
	SET @RETURN_ERROR_NEEDS_UNDO			= 3

	-------------------------------------------------------
	-- Begin
	-------------------------------------------------------

	BEGIN TRY

	--BEGIN TRANSACTION

		-------------------------------------------------------
		-- Date Input Validation
		-------------------------------------------------------
		DECLARE
			@ProcessDate datetime
			,@ProcessDate_PK decimal(18,0)
			,@OrderCutoffHour decimal(18,0)
		
		SELECT @OrderCutoffHour = OrderCutoffHour FROM Trading_Settings

		SET @ProcessDate = TBS_Trading.dbo.f_Trading_GetNextExecutionDateForTransactions()
		SELECT @ProcessDate_PK = ProcessDate_PK FROM Process_Date WHERE ProcessDate = @ProcessDate
		SET @ProcessDate = DATEADD(hh,@OrderCutoffHour,
						Convert(datetime,
							Convert(varchar, @ProcessDate, 101)
							)
						)

		DECLARE @ErrorMessage varchar(4000)

		IF (@ProcessDate > GETDATE())
		BEGIN
			-- You must wait until this Month's Trade Date and Cutoff Time to run the process
			SELECT @RETURN_ERROR_NOT_READY
			RETURN
		END

		IF ((SELECT ProcessStatus_FK FROM Process_Date WHERE ProcessDate_PK = @ProcessDate_PK) <> @PROCESS_STATUS_PENDING)
		BEGIN
			-- It appears the process did not complete successfully during the last run. You must first run the UNDO process before re-running
			SELECT @RETURN_ERROR_NEEDS_UNDO
			RETURN
		END

		--Set Running
		UPDATE Process_Date SET 
			  ProcessStatus_FK	= @PROCESS_STATUS_RUNNING
			, Details			= 'In Progress...'
			, ModifiedDateTime	= getdate()
		WHERE ProcessDate_PK = @ProcessDate_PK

		-------------------------------------------------------
		-- Create Temp Tables
		-------------------------------------------------------

		IF( OBJECT_ID('tempdb..#MarketBuyOrderSummary') IS NOT NULL ) DROP TABLE #MarketBuyOrderSummary

		CREATE TABLE #MarketBuyOrderSummary (
		     Order_FK			decimal(18,0) NOT NULL
		    ,Buy_OrderId        varchar(30)   NOT NULL 
			,Buy_Price          decimal(18,4) NOT NULL
			,Buy_Qty            decimal(18,2) NOT NULL
			,EntryDate			datetime NOT NULL
			,BuyUser_FK			decimal(18,0) NOT NULL
		)
		
		IF( OBJECT_ID('tempdb..#MarketSellOrderSummary') IS NOT NULL ) DROP TABLE #MarketSellOrderSummary

		CREATE TABLE #MarketSellOrderSummary (
			 Order_FK			decimal(18,0)	NOT NULL
			,Sell_OrderId        varchar(30)		NOT NULL
			,Sell_Price          decimal(18,4)	NOT NULL
			,Sell_Qty            decimal(18,2)	NOT NULL
			,EntryDate			datetime		NOT NULL
			,SellUser_FK			decimal(18,0)	NOT NULL
			,Buyer_Order_FK		decimal(18,0)	NOT NULL
		)

		IF( OBJECT_ID('tempdb..#TransactionsSummary') IS NOT NULL ) DROP TABLE #TransactionsSummary

		CREATE TABLE #TransactionsSummary (
			id					decimal(18,0)	NOT NULL IDENTITY(1,1)
			,Quantity			decimal(18,2)	NOT NULL
			,Price				decimal(18,4)	NOT NULL
			,Buy_User_FK        decimal(18,0)	NOT NULL
			,Buy_Order_FK       decimal(18,0)	NOT NULL
			,Sell_User_FK		decimal(18,0)	NOT NULL
			,Sell_Order_FK		decimal(18,0)	NOT NULL
			,EffectiveDate		datetime		NOT NULL
		)

		-------------------------------------------------------
		-- Sort Buys
		-- Sorted by:
		--		Price, high to low, per section 8.(i)
		--		EntryDate, first to last 8 (ii)
		--		Quantity (To match screen, because that's what Nate had)
		-- Filtered By:
		--		Open and Partial Orders
		-------------------------------------------------------

		INSERT INTO #MarketBuyOrderSummary (
			Order_FK
			,Buy_OrderId
			,Buy_Price 
			,Buy_Qty
			,EntryDate
			,BuyUser_FK				 
		)
		SELECT
			Order_PK
			,TBS_Trading.dbo.f_GetUserOrderNumber(o.Order_PK) 
			,o.Price
			,o.Quantity
			,o.EntryDate
			,o.User_FK
		FROM User_Orders O WITH (nolock)
		WHERE o.Type_FK = @ORDER_TYPE_BUY
			AND o.EntryDate < @ProcessDate
			AND (
			o.Status_FK = @ORDER_STATUS_OPEN
			OR o.Status_FK = @ORDER_STATUS_PARTIAL )
		ORDER BY o.Price DESC, o.EntryDate ASC, o.Quantity ASC
				
				
		-------------------------------------------------------
		-- Sort Sells
		--Sorted by:
		--		Price, low to high, 8.(i)
		--		EntryDate, first to last 8 (ii)
		--		Quantity (To match screen, because that's what Nate had)
		-- Filtered By:
		--		Open and Partial Orders
		-------------------------------------------------------

		INSERT INTO #MarketSellOrderSummary (
			Order_FK
			,Sell_OrderId
			,Sell_Price
			,Sell_Qty
			,EntryDate
			,SellUser_FK
			,Buyer_Order_FK
		)
		SELECT
			Order_PK
			,TBS_Trading.dbo.f_GetUserOrderNumber(o.Order_PK) 
			,o.Price
			,o.Quantity
			,EntryDate
			,o.User_FK
			,0
		FROM User_Orders O
		WHERE o.Type_FK = @ORDER_TYPE_SELL
			--AND o.User_FK = @User_FK
			AND o.EntryDate < @ProcessDate
			AND (
			     o.Status_FK = @ORDER_STATUS_OPEN
			     OR o.Status_FK = @ORDER_STATUS_PARTIAL )
		ORDER BY o.Price ASC, o.EntryDate ASC, o.Quantity ASC

		-------------------------------------------------------
		-- Declare Variables
		-------------------------------------------------------

		DECLARE
			@Quantity			decimal(18,2)
			,@MaxQuantity		decimal(18,2)
			,@TransactionID		decimal(18,0)
			,@SellId			decimal(18,0)
			,@BuyId				decimal(18,0)
			,@MaxPrice			decimal(18,4)
			,@MinPrice			decimal(18,4)
			,@AveragePrice		decimal(18,4)
			,@Sell_Qty			decimal(18,2)
			,@Buy_Qty			decimal(18,2)


		-------------------------------------------------------
		-- --Verify that Quantity in SELL ORDERS is not greater than Users available PENDING QUANTITY
		-------------------------------------------------------
		EXEC p_Trading_ValidateUserBalances
		

		-------------------------------------------------------
		-- Get Initial Values
		-------------------------------------------------------
		
		--Using Top 1 because table is already sorted
		SELECT TOP 1 @SellId = ISNULL(Order_FK,0), @MinPrice = ISNULL(Sell_Price,0) FROM #MarketSellOrderSummary WHERE Sell_Qty <> 0
		SELECT TOP 1 @BuyId = ISNULL(Order_FK,0), @MaxPrice = ISNULL(Buy_Price,0) FROM #MarketBuyOrderSummary WHERE Buy_Qty <> 0
		SELECT @Sell_Qty = Sell_Qty FROM #MarketSellOrderSummary WHERE Order_FK = @SellId
		SELECT @Buy_Qty = Buy_Qty FROM #MarketBuyOrderSummary WHERE Order_FK = @BuyId

		--Sets the corresponding buy order for the sell (So I can join against it later)
		UPDATE #MarketSellOrderSummary SET
			Buyer_Order_FK = @BuyId
		WHERE Order_FK = @SellId

		-------------------------------------------------------
		-- Start Loop
		-------------------------------------------------------
		
		-- Per section 9.C, once the highest bid price is lower than the lowest ask price, trading stops
		-- Or if either value is 0 (Signals there are no rows left)
		WHILE (@MaxPrice >= @MinPrice AND @SellId <> 0 AND @BuyId <> 0 AND @Sell_Qty<> 0 AND @Buy_Qty <> 0)
		BEGIN

				-- Per Section 9.A and 9.B:
				-- If the highest bid price and lowest ask price are equal, use that price
				-- If not, use the average of the two
				-- Of course, the average of two values which are equal is that price anyway, so I just take the average in all circumstances
				SET @AveragePrice = (@MaxPrice + @MinPrice)/2

				-- Per Section 9.A and 9.B:
				-- When executing a trase, use the lower number of units between the two trades
				SELECT @Quantity =
					CASE
						WHEN (SELECT Sell_Qty FROM #MarketSellOrderSummary WHERE Order_FK = @SellId) <
							(SELECT Buy_Qty FROM #MarketBuyOrderSummary WHERE Order_FK = @BuyId)
						THEN (SELECT Sell_Qty FROM #MarketSellOrderSummary WHERE Order_FK = @SellId)
						ELSE (SELECT Buy_Qty FROM #MarketBuyOrderSummary WHERE Order_FK = @BuyId)
					END

				--Insert Into Table
				INSERT INTO #TransactionsSummary (
					 Quantity
					,Price
					,Buy_User_FK
					,Buy_Order_FK
					,Sell_User_FK
					,Sell_Order_FK
					,EffectiveDate )
				SELECT
					@Quantity
					,@AveragePrice
					,B.BuyUser_FK
					,B.Order_FK
					,S.SellUser_FK
					,S.Order_FK
					,GETDATE()
				FROM 
					#MarketSellOrderSummary S
					INNER JOIN #MarketBuyOrderSummary B ON S.Buyer_Order_FK = B.Order_FK
					WHERE S.Order_FK = @SellId AND B.Order_FK = @BuyId

				--Update temp tables
				UPDATE #MarketBuyOrderSummary SET
					Buy_Qty = Buy_Qty - @Quantity
				WHERE Order_FK = @BuyId
			
				UPDATE #MarketSellOrderSummary SET
					Sell_Qty = Sell_Qty - @Quantity
				WHERE Order_FK = @SellId
				
			--Get Min Ask and Max Sell
			SELECT TOP 1 @SellId = ISNULL(Order_FK,0), @MinPrice = ISNULL(Sell_Price,0) FROM #MarketSellOrderSummary WHERE Sell_Qty <> 0
			SELECT TOP 1 @BuyId = ISNULL(Order_FK,0), @MaxPrice = ISNULL(Buy_Price,0) FROM #MarketBuyOrderSummary WHERE Buy_Qty <> 0
			SELECT @Sell_Qty = Sell_Qty FROM #MarketSellOrderSummary WHERE Order_FK = @SellId
			SELECT @Buy_Qty = Buy_Qty FROM #MarketBuyOrderSummary WHERE Order_FK = @BuyId
		


			UPDATE #MarketSellOrderSummary SET
				Buyer_Order_FK = @BuyId
			WHERE Order_FK = @SellId

		END

		INSERT INTO User_Transactions (
			Quantity
			,Price
			,Security_FK
			,Buy_User_FK
			,Buy_Order_FK
			,Sell_User_FK
			,Sell_Order_FK
			,EffectiveDate
			,TransactionStatus_FK
			,ProcessDate_FK )
		SELECT
			Quantity
			,Price
			,@SECURITY_FK
			,Buy_User_FK
			,Buy_Order_FK
			,Sell_User_FK
			,Sell_Order_FK
			,EffectiveDate 
			,@TRANSACTION_STATUS_PENDING
			,@ProcessDate_PK
		FROM #TransactionsSummary

		--Update real table quantities
		UPDATE User_Orders SET
			Quantity = Buy_Qty
		FROM User_Orders O
		INNER JOIN #MarketBuyOrderSummary B ON O.Order_PK = B.Order_FK

		--Update real table quantities
		UPDATE User_Orders SET
			Quantity = Sell_Qty
		FROM User_Orders O
		INNER JOIN #MarketSellOrderSummary S ON O.Order_PK = S.Order_FK

		--Update Real Tables Statuses
		UPDATE User_Orders SET Status_FK = @ORDER_STATUS_PARTIAL WHERE Quantity <> Original_Quantity AND Quantity <> 0
		UPDATE User_Orders SET Status_FK = @ORDER_STATUS_Filled WHERE Quantity = 0

		--Update Balances Table Pending Balances - Sellers
		UPDATE User_Balances SET
			Pending_Quantity = Pending_Quantity - T.Quantity
		FROM
			User_Balances B
			INNER JOIN (
				SELECT SUM(Quantity) AS Quantity, Sell_User_FK FROM User_Transactions GROUP BY Sell_User_FK) T ON T.Sell_User_FK = B.User_FK

		--Update Balances Table Pending Balances - Buyers
		UPDATE User_Balances SET
			Pending_Quantity = Pending_Quantity + T.Quantity
		FROM
			User_Balances B
			INNER JOIN (
				SELECT SUM(Quantity) AS Quantity, Buy_User_FK FROM User_Transactions GROUP BY Buy_User_FK) T ON T.Buy_User_FK = B.User_FK


		IF( OBJECT_ID('tempdb..#TransactionsSummary') IS NOT NULL ) DROP TABLE #TransactionsSummary
		IF( OBJECT_ID('tempdb..#MarketBuyOrderSummary') IS NOT NULL ) DROP TABLE #MarketBuyOrderSummary
		IF( OBJECT_ID('tempdb..#MarketSellOrderSummary') IS NOT NULL ) DROP TABLE #MarketSellOrderSummary
		
		--COMMIT TRANSACTION

		--Set Success
		UPDATE Process_Date SET 
			  ProcessStatus_FK	= @PROCESS_STATUS_SUCCESS
			, Details			= 'Done'
			, ModifiedDateTime	= getdate()
		WHERE ProcessDate_PK = @ProcessDate_PK

		-----------------------------------
		-- Return Success
		-----------------------------------
		SELECT @RETURN_SUCCESS
		RETURN

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
		--ROLLBACK TRANSACTION

		UPDATE Process_Date SET 
			  ProcessStatus_FK	= @PROCESS_STATUS_FAILED
			, Details			= CONVERT(varchar(500), @ErrMsg)
			, ModifiedDateTime	= getdate()
		WHERE ProcessDate_PK = @ProcessDate_PK
	END CATCH

END
GO
