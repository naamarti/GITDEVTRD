SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Val Catrini
-- Create date: Sep 10, 2008
-- Modified: 	Feb 24, 2009; Val Catrini, added TRY/CATCH
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[p_j_GetRandomChallengeQuestion]
	@User_FK decimal(18,0)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		CREATE TABLE #questions (
			 row_id			decimal(18,0) IDENTITY(1,1)
			,question_id	decimal(18,0) )
	
		INSERT INTO #questions (question_id)
		SELECT ChallengeQuestion_FK
		FROM AppUser_ChallengeQuestion_Relationship where User_FK = @User_FK
	
		DECLARE
			 @max_row_id			int
			,@random_id				int
			,@random_question_id	int
	
		SELECT @max_row_id = MAX(row_id) FROM #questions
		SET @random_id = CAST(RAND() * @max_row_id AS INT) + 1
		SELECT @random_question_id = question_id FROM #questions WHERE row_id = @random_id
	
		SELECT ChallengeQuestion_PK, ChallengeQuestion
		FROM ChallengeQuestion
		WHERE ChallengeQuestion_PK = @random_question_id

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
