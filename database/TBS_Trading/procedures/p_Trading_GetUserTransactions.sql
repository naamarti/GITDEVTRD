SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Created:  	14 Dec 2012 DJM
 * Modified:	
 *
 * Get Users's Transaction History
 *
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_GetUserTransactions]
	@User_FK    	decimal(18,0)
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
			, CASE T.Buy_User_FK
				WHEN @User_FK THEN 'BUY'
				ELSE 'SELL' 
			  END AS TransactionType
		FROM User_Transactions T
		INNER JOIN Securities S ON S.Security_PK = T.Security_FK
		WHERE T.Buy_User_FK = @User_FK 
			OR T.Sell_User_FK = @User_FK
		ORDER BY T.EffectiveDate DESC
		
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
