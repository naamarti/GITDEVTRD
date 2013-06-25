SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Marek Komza
-- Create date: 03/21/2013
-- Description:	Retrieves LastExecutionDate
-- =============================================
CREATE FUNCTION [dbo].[f_GetLastExecutionDate] 
(
)
RETURNS datetime
AS
BEGIN
	DECLARE @LastExecutionDate datetime
	DECLARE @CurrentDateTime datetime

	SELECT @currentDateTime = GETDATE()
	
	SELECT TOP 1 @LastExecutionDate = PD.ProcessDate
	FROM Process_Date PD
	INNER JOIN ProcessStatus_Types PS ON PS.ProcessStatus_PK = PD.ProcessStatus_FK
	WHERE (CAST(PD.ProcessDate AS datetime) + CAST(dbo.f_GetCutoffTime() AS datetime)) < @CurrentDateTime 
		and PS.ProcessStatus = 'Success'
	ORDER BY PD.ProcessDate DESC
	
	RETURN @LastExecutionDate

END
GO
