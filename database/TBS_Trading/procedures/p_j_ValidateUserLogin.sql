SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- ============================================================================
-- Author:		Val Catrini
-- Create date: Sep 11, 2008
-- Modified: 	Feb 24, 2009; Val Catrini, added TRY/CATCH
--				1 Nov 2009 - DJM - Added invalid login logic
-- Description:	
-- ============================================================================
CREATE PROCEDURE [dbo].[p_j_ValidateUserLogin]
	@UserName			varchar(30),
	@UserPassword		char(40)		-- encrypted password
	
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		DECLARE
			@User_PK			decimal(18,0),
			@ValidPassword		char(40),
			@NumBeforeLockout 	int
		
		SET @NumBeforeLockout = (SELECT NumLoginAttemptsBeforeLockout FROM AppConfiguration)
		
		SELECT 	@User_PK = User_PK, 
				@ValidPassword = UserPassword
		FROM AppUser WITH (NOLOCK)
		WHERE UserName = @UserName
		
		IF (@User_PK IS NOT NULL)
		BEGIN

			-------------------------------------
			-- User exists, AND password is VALID 		
			-------------------------------------
			IF( @ValidPassword = @UserPassword )
			BEGIN

				EXEC p_Trading_AddActivityLogEntry
					@User_FK	= @User_PK,
					@Activity	= 'Logon Success'
	
				-- reset invalid login count if not already locked
				UPDATE AppUser SET LoginFailedAttempts = 0
				WHERE User_PK = @User_PK
				AND IsLocked = 0

				SELECT User_PK, UserName, IsPasswordReset, IsSecretQuestionsSet, 
					   PasswordChangeDateTime, IsMandatoryContactInfoSet, Contact_FK,
					   IsLocked, 1 as IsPasswordMatch, LoginFailedAttempts
				FROM AppUser WITH (NOLOCK)
				WHERE User_PK = @User_PK

			END
			ELSE	
			---------------------------------------
			-- USER exists, but password is INVALID 		
			---------------------------------------
			BEGIN

				EXEC p_Trading_AddActivityLogEntry
					@User_FK	= @User_PK,
					@Activity	= 'Logon Failure - Invalid Password'

				UPDATE AppUser SET
					LoginFailedAttempts = LoginFailedAttempts + 1,
					IsLocked = CASE WHEN ((LoginFailedAttempts + 1) >= @NumBeforeLockout) THEN 1 ELSE 0 END
				WHERE User_PK = @User_PK

				SELECT User_PK, UserName, IsPasswordReset, IsSecretQuestionsSet, 
					   PasswordChangeDateTime, IsMandatoryContactInfoSet, Contact_FK,
					   IsLocked, 0 as IsPasswordMatch, LoginFailedAttempts
				FROM AppUser WITH (NOLOCK)
				WHERE User_PK = @User_PK

			END
			
		END
		ELSE	

		---------------------------------------
		-- USER does not exist		
		---------------------------------------
		BEGIN
			
			-- Exit with empty resultset
			SELECT 1 FROM AppUser WITH (NOLOCK)
			WHERE 1 = 2

		END
	
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
