SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO
CREATE FUNCTION [dbo].[f_Trading_GetUndoDateForTransactions] 
(
)
RETURNS datetime
AS
BEGIN
	DECLARE @LastExecutionDate datetime

	SELECT TOP 1 @LastExecutionDate = PD.ProcessDate
	FROM Process_Date PD
	INNER JOIN ProcessStatus_Types PS ON PS.ProcessStatus_PK = PD.ProcessStatus_FK
	WHERE PS.ProcessStatus <> 'Pending'
	ORDER BY PD.ProcessDate DESC

	RETURN @LastExecutionDate

END

GO
