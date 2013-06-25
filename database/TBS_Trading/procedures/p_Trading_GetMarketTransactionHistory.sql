SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Created:  	
 * Modified:	
 *
 * Get Market Transaction History
 *
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_GetMarketTransactionHistory]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		SELECT 
			  dbo.f_GetUserTransactionNumber(T.Transaction_PK) AS TransactionId
			, S.Name AS SecurityName
			, T.Quantity
			, T.Price
			, T.EffectiveDate
		FROM User_Transactions T
		INNER JOIN Securities S ON S.Security_PK = T.Security_FK
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
