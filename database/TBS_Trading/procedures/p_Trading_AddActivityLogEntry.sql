SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Created:  	18 Dec 2012 DJM
 * Modified:	
 *
 * Add Activity Log Entry
 *
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_AddActivityLogEntry]
 	  @User_FK    	decimal(18,0),
 	  @Activity		varchar(255)

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		INSERT INTO ActivityLog( User_FK, Activity, EntryDateTime ) VALUES ( @User_FK, @Activity, getDate() )
				
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
