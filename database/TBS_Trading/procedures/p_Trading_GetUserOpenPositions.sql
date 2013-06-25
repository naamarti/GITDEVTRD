SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Modified:  14 Dec 2012 VC #: TBS Units Trading System
 *
 * Get Users's Open Positions
 *
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_GetUserOpenPositions]
 	  @User_FK           decimal(18,0)

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		SELECT B.*, S.Name AS SecurityName, S.Description AS SecurityDescription
		FROM User_Balances B
		INNER JOIN Securities S ON S.Security_PK = B.Security_FK
		WHERE B.User_FK = @User_FK	
		ORDER BY SecurityName

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
