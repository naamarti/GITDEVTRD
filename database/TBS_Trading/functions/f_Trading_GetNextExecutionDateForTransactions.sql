SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE FUNCTION [dbo].[f_Trading_GetNextExecutionDateForTransactions] 
(
)
RETURNS datetime
AS
BEGIN
	DECLARE @NextExecutionDate datetime


	
	SELECT TOP 1 @NextExecutionDate = PD.ProcessDate
	FROM Process_Date PD
	INNER JOIN ProcessStatus_Types PS ON PS.ProcessStatus_PK = PD.ProcessStatus_FK
	WHERE PS.ProcessStatus <> 'Success'
	ORDER BY PD.ProcessDate ASC

	RETURN @NextExecutionDate

END

GO
