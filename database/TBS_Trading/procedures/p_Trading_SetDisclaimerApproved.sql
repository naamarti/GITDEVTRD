SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Modified:  24 Jan 2013 DJM
 *
 * For the given user, set that they have approved the disclaimer.
 *
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_SetDisclaimerApproved]
	  @User_FK			decimal(18,0)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		IF EXISTS ( SELECT * FROM Trading_UserSettings WHERE User_FK = @User_FK )
		BEGIN

			UPDATE Trading_UserSettings SET
				IsDisclaimerApproved = 'TRUE'
			WHERE User_FK = @User_FK
					
		END
		ELSE
		BEGIN
		
			INSERT INTO Trading_UserSettings( User_FK, IsDisclaimerApproved ) 
			VALUES( @User_FK, 'TRUE' )
		
		END
		
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
