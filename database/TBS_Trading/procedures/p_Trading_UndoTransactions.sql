SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE PROCEDURE [dbo].[p_Trading_UndoTransactions]
	 --@ProcessDate           datetime

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;


	DECLARE @IsFail bit
	SET @IsFail = 'TRUE'
	
			-------------------------------------------------------
		-- Type Variables
		-------------------------------------------------------
		DECLARE
			  @ORDER_TYPE_BUY		        decimal(18,0)
			 ,@ORDER_TYPE_SELL		        decimal(18,0)
			 ,@IS_USER_ORDER		        decimal(18,0)
			 ,@ORDER_STATUS_OPEN	        decimal(18,0)
			 ,@ORDER_STATUS_PARTIAL	        decimal(18,0)
			 ,@ORDER_STATUS_Filled			decimal(18,0)
			 ,@TRANSACTION_STATUS_PENDING	decimal(18,0)
			 ,@PROCESS_STATUS_PENDING		decimal(18,0)
			 ,@PROCESS_STATUS_RUNNING		decimal(18,0)
			 ,@PROCESS_STATUS_SUCCESS		decimal(18,0)
			 ,@PROCESS_STATUS_FAILED		decimal(18,0)
			 ,@PROCESS_STATUS_UNDOING		decimal(18,0)

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
		SET @PROCESS_STATUS_UNDOING = (SELECT ProcessStatus_PK FROM ProcessStatus_Types WHERE ProcessStatus = 'Undoing')

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

		SET @ProcessDate = TBS_Trading.dbo.f_Trading_GetUndoDateForTransactions()
		SELECT @ProcessDate_PK = ProcessDate_PK FROM Process_Date WHERE ProcessDate = @ProcessDate
		SET @ProcessDate = DATEADD(hh,@OrderCutoffHour,
						Convert(datetime,
							Convert(varchar, @ProcessDate, 101)
							)
						)

		--Throw Error if @ProcessDate > GetDATE()
		--IF (@ProcessDate > GETDATE())
		--BEGIN

		--	DECLARE @ErrorMessage varchar(4000)
		--	SET @ErrorMessage = 'Nothing to undo!'
		--	RAISERROR(@ErrorMessage,1,1)
		--	RETURN

		--END




		--Set Undoing
		UPDATE Process_Date SET ProcessStatus_FK = @PROCESS_STATUS_UNDOING WHERE ProcessDate_PK = @ProcessDate_PK

		-------------------------------------------------------
		-- Update Quantities
		-------------------------------------------------------
		--Orders Table
		UPDATE User_Orders SET
			Quantity = O.Quantity + T.Quantity
		FROM User_Orders O
		INNER JOIN 
		(SELECT Buy_Order_FK, SUM(Quantity) AS Quantity FROM User_Transactions WHERE ProcessDate_FK = @ProcessDate_PK GROUP BY Buy_Order_FK) T
			ON O.Order_PK = T.Buy_Order_FK

		UPDATE User_Orders SET
			Quantity = O.Quantity + T.Quantity
		FROM User_Orders O
		INNER JOIN 
			(SELECT Sell_Order_FK, SUM(Quantity) AS Quantity FROM User_Transactions WHERE ProcessDate_FK = @ProcessDate_PK GROUP BY Sell_Order_FK) T
		ON O.Order_PK = T.Sell_Order_FK


		--Balances Table
		UPDATE User_Balances SET
			Pending_Quantity = B.Pending_Quantity - T.Quantity
		FROM User_Balances B
		INNER JOIN
			(SELECT Buy_User_FK, SUM(Quantity) AS Quantity FROM User_Transactions WHERE ProcessDate_FK = @ProcessDate_PK GROUP BY Buy_User_FK) T
		ON B.User_FK = T.Buy_User_FK
		

		UPDATE User_Balances SET
			Pending_Quantity = B.Pending_Quantity + T.Quantity
		FROM User_Balances B
		INNER JOIN
			(SELECT Sell_User_FK, SUM(Quantity) AS Quantity FROM User_Transactions WHERE ProcessDate_FK = @ProcessDate_PK GROUP BY Sell_User_FK) T
		ON B.User_FK = T.Sell_User_FK
		

		-------------------------------------------------------
		-- Update Order Statuses
		-------------------------------------------------------
		UPDATE User_Orders SET
			Status_FK = @ORDER_STATUS_OPEN
		WHERE Quantity = Original_Quantity

		UPDATE User_Orders SET
			Status_FK = @ORDER_STATUS_PARTIAL
		WHERE Quantity < Original_Quantity

		UPDATE User_Orders SET
			Status_FK = @ORDER_STATUS_Filled
		WHERE Quantity = 0

		-------------------------------------------------------
		-- Delete Transactions
		-------------------------------------------------------
		DELETE FROM User_Transactions WHERE ProcessDate_FK = @ProcessDate_PK

		--COMMIT TRANSACTION

		--Set Pending
		UPDATE Process_Date SET 
			  ProcessStatus_FK	= @PROCESS_STATUS_PENDING
			, Details			= 'Process undone.'
		WHERE ProcessDate_PK = @ProcessDate_PK

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
		--ROLLBACK TRANSACTION
		IF (@IsFail = 'TRUE')
		BEGIN
			UPDATE Process_Date SET ProcessStatus_FK = @PROCESS_STATUS_FAILED WHERE ProcessDate_PK = @ProcessDate_PK
		END
	END CATCH

END
GO
