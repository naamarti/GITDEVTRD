SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Val Catrini
-- Create date: July 7, 2008
-- Modified: 	Feb 24, 2009; Val Catrini, added TRY/CATCH
--              Jul 14, 2009; Dan Merkel, added Contact_FK to resultset
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[p_j_ValidateUserSession]
	@User_FK 						decimal(18,0),
	@SessionKey						char(40),
	@WorkingProgram_FK				decimal(18,0),
	@WorkingSourceInstitution_FK	decimal(18,0),
	@WorkingDate					datetime,
	@LoginInProgress    			bit
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		DECLARE @RequiredDeletedFlag bit
	
		IF @LoginInProgress = 1 
			SET @RequiredDeletedFlag = 1
		ELSE
			SET @RequiredDeletedFlag = 0
		
		UPDATE AppSession SET
			LastAccessDateTime			= getdate(),
--			WorkingProgram_FK			= CASE @WorkingProgram_FK WHEN 0 THEN WorkingProgram_FK ELSE @WorkingProgram_FK END,
--			WorkingSourceInstitution_FK	= CASE @WorkingSourceInstitution_FK WHEN 0 THEN WorkingSourceInstitution_FK ELSE @WorkingSourceInstitution_FK END,
			WorkingDate					= CASE @WorkingDate WHEN '' THEN WorkingDate ELSE @WorkingDate END
		WHERE
			User_FK						= @User_FK
			AND SessionKey				= @SessionKey
			AND IsDeleted				= @RequiredDeletedFlag
		
		IF @@ROWCOUNT = 0 
		BEGIN
			-- Invalid User/Session, exit with no results
			SELECT 1 FROM AppUser WITH (NOLOCK)
			WHERE 1 = 2
		END
		ELSE
		BEGIN
			SELECT a.User_PK, a.UserName, a.IsPasswordReset, a.IsSecretQuestionsSet,
				a.PasswordChangeDateTime, a.IsMandatoryContactInfoSet, a.Contact_FK,
				b.Session_PK, b.SessionKey, b.LoginStatus, b.WorkingDate
			FROM AppUser a WITH (NOLOCK)
			LEFT JOIN AppSession b WITH (NOLOCK) ON a.User_PK = b.User_FK
			WHERE a.User_PK = @User_FK
		END
	
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
