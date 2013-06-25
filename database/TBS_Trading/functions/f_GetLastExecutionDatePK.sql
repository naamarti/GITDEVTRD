SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Marek Komza
-- Create date: 03/21/2013
-- Description:	Retrieves LastExecutionDate
-- =============================================
CREATE FUNCTION [dbo].[f_GetLastExecutionDatePK] 
(
)
RETURNS decimal(18,0)
AS
BEGIN
	DECLARE @LastExecutionDate_PK decimal(18,0)
	DECLARE @CurrentDateTime datetime

	SELECT @currentDateTime = GETDATE()
	
	SELECT TOP 1 @LastExecutionDate_PK = PD.ProcessDate_PK
	FROM Process_Date PD
	INNER JOIN ProcessStatus_Types PS ON PS.ProcessStatus_PK = PD.ProcessStatus_FK
	WHERE (CAST(PD.ProcessDate AS datetime) + CAST(dbo.f_GetCutoffTime() AS datetime)) < @CurrentDateTime 
		and PS.ProcessStatus = 'Success'
	ORDER BY PD.ProcessDate DESC
	
	RETURN @LastExecutionDate_PK

END
GO
