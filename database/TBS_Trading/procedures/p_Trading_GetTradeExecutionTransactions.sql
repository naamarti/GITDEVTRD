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
CREATE PROCEDURE [dbo].[p_Trading_GetTradeExecutionTransactions]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		SELECT
			  dbo.f_GetUserTransactionNumber(T.Transaction_PK) AS TransactionId
			, T.Quantity
			, T.Price
			, T.Quantity * T.Price AS Total
			, T.EffectiveDate
			, T.TransactionStatus_FK
			, dbo.f_GetUserOrderNumber(T.Buy_Order_FK) AS BuyOrderNumber
			, dbo.f_GetUserOrderNumber(T.Sell_Order_FK) AS SellOrderNumber
			, BU.UserName AS 'BuyUsername'
			, SU.UserName AS 'SellUsername'
			, TS.TransactionStatus
		FROM User_Transactions T
		INNER JOIN AppUser BU ON BU.User_PK = T.Buy_User_FK
		INNER JOIN AppUser SU ON SU.User_PK = T.Sell_User_FK
		INNER JOIN TransactionStatus_Types TS ON TS.TransactionStatus_PK = T.TransactionStatus_FK
		WHERE ProcessDate_FK = dbo.f_GetLastExecutionDatePK() 
		ORDER BY Transaction_PK

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
