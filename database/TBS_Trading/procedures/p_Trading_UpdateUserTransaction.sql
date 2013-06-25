
/****** Object:  StoredProcedure [dbo].[p_Trading_UpdateUserTransaction]    Script Date: 06/12/2013 12:15:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


/* 
 * =================================================================================================
 * Modified: 24 Apr 2013 VAC #2420: TBS Units Trading System - Bug Updating Trade Status
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_UpdateUserTransaction]
	  @TransactionID			char(7)
	, @TransactionStatus_FK		decimal(18,0)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		DECLARE
			@TRAN_STATUS_TYPE_SETTLED      decimal(18,0)                 
			
		SET @TRAN_STATUS_TYPE_SETTLED  = (SELECT TransactionStatus_PK FROM TransactionStatus_Types WHERE TransactionStatus = 'SETTLED') 

		UPDATE User_Transactions SET
			TransactionStatus_FK = @TransactionStatus_FK
		WHERE 
			Transaction_PK = dbo.f_GetUserTransactionNumberPK(@TransactionID)
			
			
		IF ( @TransactionStatus_FK = @TRAN_STATUS_TYPE_SETTLED )
		BEGIN
			UPDATE User_Balances
			SET
				Quantity = User_Balances.Quantity - T.Quantity
			FROM 
				User_Transactions T
			WHERE
				T.Transaction_PK =  dbo.f_GetUserTransactionNumberPK(@TransactionID) AND
				User_Balances.User_FK = T.Sell_User_FK
				
			UPDATE User_Balances
			SET
				Quantity = User_Balances.Quantity + T.Quantity
			FROM 
				User_Transactions T
			WHERE
				T.Transaction_PK =  dbo.f_GetUserTransactionNumberPK(@TransactionID) AND
				User_Balances.User_FK = T.Buy_User_FK
				
		END
				
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
