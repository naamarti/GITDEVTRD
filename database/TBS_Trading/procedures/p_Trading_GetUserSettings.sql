SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Created:  	18 Jan 2013 DJM
 * Modified:	
 *
 * Get User Settings.  Currently, at this time, there is only the IsDisclaimerApproved flag, but we
 * most likely will add additional user settings in the future.
 *
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_GetUserSettings]
 	  @User_FK    	decimal(18,0)

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		SELECT 
			User_FK,
			IsDisclaimerApproved	
		FROM Trading_UserSettings 
		WHERE User_FK = @User_FK 
		
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
