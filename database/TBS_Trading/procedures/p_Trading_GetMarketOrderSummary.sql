SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Nate Martin
-- Create date: Dec 13, 2012
--
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[p_Trading_GetMarketOrderSummary]
 	  @User_FK           decimal(18,0)
	 ,@StartDate         datetime
	 ,@EndDate           datetime

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;


	DECLARE
		 @MAX_SUMMARY_RESULTS           int
		 ,@ORDER_TYPE_BUY		        int
		 ,@ORDER_TYPE_SELL		        int
		 ,@IS_USER_ORDER		        int
		 ,@ORDER_STATUS_OPEN	        int
		 ,@ORDER_STATUS_PARTIAL	        int
		 ,@BUY_CONVERGENCE_ORDER_FK		int
		 ,@SELL_CONVERGENCE_ORDER_FK	int


-- 	 ,@User_FK           decimal(18,0)
--	 ,@StartDate         datetime
--	 ,@EndDate           datetime
	 
--SET @User_FK = (SELECT User_PK FROM AppUser WHERE UserName = 'B05325')
--SET @StartDate = '2013/01/01'
--SET @EndDate = '2013/12/30'
		
		SET @MAX_SUMMARY_RESULTS = 100
		SET @ORDER_TYPE_BUY  = (SELECT Type_PK FROM Order_Types WHERE Code = 'BUY')
		SET @ORDER_TYPE_SELL = (SELECT Type_PK FROM Order_Types WHERE Code = 'SELL')
		SET @ORDER_STATUS_OPEN = (SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'OPEN')
		SET @ORDER_STATUS_PARTIAL = (SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'PARTIAL')

		
		SET @BUY_CONVERGENCE_ORDER_FK =		isnull((SELECT TOP 1 Order_PK FROM User_Orders
											 WHERE Type_FK = @ORDER_TYPE_BUY AND Status_FK IN (@ORDER_STATUS_OPEN, @ORDER_STATUS_PARTIAL)
											 ORDER BY Price DESC, EntryDate ASC, Quantity DESC),0)
										 
		SET @SELL_CONVERGENCE_ORDER_FK =	isnull ((SELECT TOP 1 Order_PK FROM User_Orders
											 WHERE Type_FK = @ORDER_TYPE_SELL AND Status_FK IN (@ORDER_STATUS_OPEN, @ORDER_STATUS_PARTIAL)
											 ORDER BY Price ASC, EntryDate ASC, Quantity DESC),0)

	BEGIN TRY

		IF( OBJECT_ID('tempdb..#MarketOrderSummary') IS NOT NULL ) DROP TABLE #MarketOrderSummary

		CREATE TABLE #MarketOrderSummary (
			 Bid_Price          decimal(18,4) NOT NULL
			,Bid_Qty            decimal(18,2) NOT NULL
			,Bid_OrderId        varchar(30)   NOT NULL
			,Bid_Order_FK       decimal(18,0) NOT NULL
			,Bid_Time           varchar(40)	  NOT NULL
			,Ask_Price          decimal(18,4) NOT NULL
			,Ask_Qty            decimal(18,2) NOT NULL
			,Ask_OrderId        varchar(30)   NOT NULL
			,Ask_Order_FK       decimal(18,0) NOT NULL
			,Ask_Time           varchar(40)	  NOT NULL
			,Convergence_Row    bit NOT NULL  --sure, we can name this something better
		)
		
		IF( OBJECT_ID('tempdb..#MarketBuyOrderSummary') IS NOT NULL ) DROP TABLE #MarketBuyOrderSummary

		CREATE TABLE #MarketBuyOrderSummary (
		     Order_FK			decimal(18,0) NOT NULL
		    ,Bid_OrderId        varchar(30)   NOT NULL 
			,Bid_Price          decimal(18,4) NOT NULL
			,Bid_Qty            decimal(18,2) NOT NULL
			,EntryDate			datetime NOT NULL
		)
		
		IF( OBJECT_ID('tempdb..#MarketSellOrderSummary') IS NOT NULL ) DROP TABLE #MarketSellOrderSummary

		CREATE TABLE #MarketSellOrderSummary (
			 Order_FK			decimal(18,0) NOT NULL
			,Ask_OrderId        varchar(30)   NOT NULL
			,Ask_Price          decimal(18,4) NOT NULL
			,Ask_Qty            decimal(18,2) NOT NULL
			,EntryDate			datetime NOT NULL
		)
		
		--NOTE: These table updates are separated out in case we do want to combine the non-current-user orders for the same price into one consolidated quantity.
		--      This may however be tough though, since the user could in fact be between others date-wise with the same price.
		
		

		-------------------------------------------------------
		-- BID for User 
		-------------------------------------------------------
			INSERT INTO #MarketBuyOrderSummary (
				 Order_FK
				,Bid_OrderId
				,Bid_Price 
				,Bid_Qty
				,EntryDate				 
			)
			SELECT TOP 100
				 Order_PK
				,TBS_Trading.dbo.f_GetUserOrderNumber(o.Order_PK) 
				,o.Price
				,o.Quantity
				,o.EntryDate
			FROM User_Orders O WITH (nolock)
			WHERE o.Type_FK = @ORDER_TYPE_BUY
			  AND o.User_FK = @User_FK
			  AND o.EntryDate BETWEEN @StartDate AND @EndDate
			  AND o.Status_FK IN (@ORDER_STATUS_OPEN, @ORDER_STATUS_PARTIAL)
			ORDER BY o.Price ASC, o.EntryDate ASC, o.Quantity ASC
		

		-------------------------------------------------------
		-- BID for Everyone but user User 
		-------------------------------------------------------
			INSERT INTO #MarketBuyOrderSummary (
				 Order_FK
				,Bid_OrderId
				,Bid_Price 
				,Bid_Qty
				,EntryDate
			)
			SELECT TOP 100
				 Order_PK
				,''  
				,o.Price
				,o.Quantity
				,EntryDate
			FROM User_Orders O WITH (nolock)
			WHERE o.Type_FK = @ORDER_TYPE_BUY
			  AND o.User_FK <> @User_FK
			  AND o.EntryDate BETWEEN @StartDate AND @EndDate
			  AND o.Status_FK IN (@ORDER_STATUS_OPEN, @ORDER_STATUS_PARTIAL)
			ORDER BY o.Price ASC, o.EntryDate ASC, o.Quantity ASC
				
				

			
				
				
		-------------------------------------------------------
		-- ASK for User 
		-------------------------------------------------------
			INSERT INTO #MarketSellOrderSummary (
				 Order_FK
				,Ask_OrderId
				,Ask_Price
				,Ask_Qty
				,EntryDate
			)
			SELECT TOP 100
				 Order_PK
				,TBS_Trading.dbo.f_GetUserOrderNumber(o.Order_PK) 
				,o.Price
				,o.Quantity
				,EntryDate
			FROM User_Orders O
			WHERE o.Type_FK = @ORDER_TYPE_SELL
			  AND o.User_FK = @User_FK
			  AND o.EntryDate BETWEEN @StartDate AND @EndDate
			  AND o.Status_FK IN (@ORDER_STATUS_OPEN, @ORDER_STATUS_PARTIAL)
			ORDER BY o.Price ASC, o.EntryDate ASC, o.Quantity ASC
		
	

		-------------------------------------------------------
		-- ASK for Everyone but user User 
		---------------------------------------------------------
			INSERT INTO #MarketSellOrderSummary (
				 Order_FK
				,Ask_OrderId 
				,Ask_Price
				,Ask_Qty
				,EntryDate
			)
			SELECT TOP 100
				 Order_PK
				,''
				,o.Price
				,o.Quantity
				--,''
				,EntryDate
			FROM User_Orders O
			WHERE o.Type_FK = @ORDER_TYPE_SELL
			  AND o.User_FK <> @User_FK
			  AND o.EntryDate BETWEEN @StartDate AND @EndDate
			  AND o.Status_FK IN (@ORDER_STATUS_OPEN, @ORDER_STATUS_PARTIAL)
			ORDER BY o.Price ASC, o.EntryDate ASC, o.Quantity ASC
				
		
			

				
		-------------------------------------------------------
		-- Create Summary Table:BID
		-------------------------------------------------------			
			INSERT INTO #MarketOrderSummary (
				 Bid_Price 
				,Bid_Qty 
				,Bid_OrderId
				,Bid_Order_FK
				,Bid_Time
				,Ask_Price
				,Ask_Qty 
				,Ask_OrderId
				,Ask_Order_FK
				,Ask_Time
				,Convergence_Row
			)
			SELECT TOP 100
				 Bid_Price
				,Bid_Qty
				,Bid_OrderId
				,Order_FK
				,CONVERT(varchar(40), EntryDate, 110) + ' ' + CONVERT(varchar(40), EntryDate, 108)
				,0
				,0
				,''
				,0
				,0
				,0
			FROM #MarketBuyOrderSummary MS
			ORDER BY Bid_Price ASC, EntryDate DESC, Bid_Qty ASC
				



		------------------------------------------------------------------------------------------------------------
		-- Create Summary Table:Add Convergence Information and the accompanying ASK *IF* there is a convergence.
		-- *IF* there are no entries for either BID or for ASK then there can be no convergence
		------------------------------------------------------------------------------------------------------------		
			DECLARE 
				 @Convergence_ASK_PRICE		decimal(18,2)
				,@Convergence_ASK_QTY		decimal(18,2)
				,@Convergence_ASK_OrderId	varchar(30)
				,@Convergence_ASK_Order_FK  decimal(18,2)
				,@Convergence_ASK_Time		varchar(40)
			
		IF @BUY_CONVERGENCE_ORDER_FK > 0 AND @SELL_CONVERGENCE_ORDER_FK > 0
		BEGIN
			SELECT 	
				 @Convergence_ASK_PRICE    = Ask_Price
				,@Convergence_ASK_QTY      = Ask_Qty
				,@Convergence_ASK_OrderId  = Ask_OrderId
				,@Convergence_ASK_Order_FK = Order_FK 
				,@Convergence_ASK_Time	   = CONVERT(varchar(40), EntryDate, 110) + ' ' + CONVERT(varchar(40), EntryDate, 108)
			FROM #MarketSellOrderSummary 
			WHERE Order_FK = @SELL_CONVERGENCE_ORDER_FK
							
			
			UPDATE #MarketOrderSummary		
			SET Ask_Price    = @Convergence_ASK_PRICE
			   ,Ask_Qty      = @Convergence_ASK_QTY
			   ,Ask_OrderId  = @Convergence_ASK_OrderId
			   ,Ask_Order_FK = @Convergence_ASK_Order_FK
			   ,Ask_Time     = @Convergence_ASK_Time
			   ,Convergence_Row = 1
			FROM #MarketOrderSummary MOS
			WHERE Bid_Order_FK = @BUY_CONVERGENCE_ORDER_FK
		END
		ELSE
		BEGIN
			SET @BUY_CONVERGENCE_ORDER_FK = 0
			SET @SELL_CONVERGENCE_ORDER_FK = 0
		END
				
		-------------------------------------------------------
		-- Create Summary Table:ASK
		-------------------------------------------------------			
			INSERT INTO #MarketOrderSummary (
				 Bid_Price 
				,Bid_Qty 
				,Bid_OrderId
				,Bid_Order_FK
				,Bid_Time
				,Ask_Price
				,Ask_Qty 
				,Ask_OrderId
				,Ask_Order_FK 
				,Ask_Time
				,Convergence_Row
			)
			SELECT TOP 100
				 0
				,0
				,''
				,0
				,0
				,Ask_Price
				,Ask_Qty
				,Ask_OrderId
				,Order_FK
				,CONVERT(varchar(40), EntryDate, 110) + ' ' + CONVERT(varchar(40), EntryDate, 108)
				,0
			FROM #MarketSellOrderSummary MS
			WHERE Order_FK <> @SELL_CONVERGENCE_ORDER_FK
			ORDER BY Ask_Price ASC, EntryDate ASC, Ask_Qty ASC
			
							
		
		SELECT * FROM #MarketOrderSummary 

		
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
