SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Modified: 	
--
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[p_Trading_GetTradeExecutionStatus]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		DECLARE
			@ProcessDate datetime
		
		SET @ProcessDate = (
			SELECT TOP 1 ProcessDate
			FROM Process_Date PD
			INNER JOIN ProcessStatus_Types PS ON PS.ProcessStatus_PK = PD.ProcessStatus_FK
			WHERE PS.ProcessStatus = 'Pending'
			ORDER BY ProcessDate )
			
		SELECT 
			  PD.ProcessDate
			, PS.ProcessStatus
			, PD.ModifiedDateTime
			, AU.UserName
			, PD.Details
		FROM Process_Date PD
		INNER JOIN ProcessStatus_Types PS ON PS.ProcessStatus_PK = PD.ProcessStatus_FK
		LEFT JOIN AppUser AU ON AU.User_PK = PD.User_FK
		WHERE ProcessDate <= @ProcessDate
		ORDER BY ProcessDate DESC

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
