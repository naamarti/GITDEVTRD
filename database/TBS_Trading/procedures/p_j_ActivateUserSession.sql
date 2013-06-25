SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Val Catrini
-- Create date: Sep 13, 2008
-- Modified: 	Feb 24, 2009; Val Catrini, added TRY/CATCH
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[p_j_ActivateUserSession]
	@User_FK 			decimal(18,0),
	@SessionKey			char(40)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		UPDATE AppSession SET
			LoginStatus			= 2,
			LoginDateTime		= getdate(),
			LastAccessDateTime	= getdate(),
			IsDeleted			= 0
		WHERE
			User_FK				= @User_FK
			AND SessionKey		= @SessionKey

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH
	
END
GO
