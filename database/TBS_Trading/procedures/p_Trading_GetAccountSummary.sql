SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Created:  	17 Dec 2012 DJM
 * Modified:	
 *
 * Get User Account Summary
 *
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_GetAccountSummary]
 	  @User_FK    	decimal(18,0)

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		SELECT UserName, ISNULL( SUM(Quantity), 0 ) AS Quantity
		FROM AppUser AU
		LEFT JOIN User_Balances UB ON
			UB.User_FK = AU.User_PK	
		WHERE AU.User_PK = @User_FK
		GROUP BY UserName
				
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
