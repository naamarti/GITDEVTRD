SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Nate Martin
-- Create date: Mar 25, 2013
-- Modified: 	
-- Description:	
-- =============================================
CREATE FUNCTION [dbo].[f_Trading_GetNextExecutionDateForOrders] 
(
	 @dummy			decimal(18,0)
)
RETURNS datetime
AS
BEGIN
	DECLARE @NextExecutionDate datetime
		   ,@OrderCutoffHour decimal(18,0)
		
	SELECT @OrderCutoffHour = OrderCutoffHour FROM Trading_Settings
	
	SELECT TOP 1 @NextExecutionDate = PD.ProcessDate
	FROM Process_Date PD
	INNER JOIN ProcessStatus_Types PS ON PS.ProcessStatus_PK = PD.ProcessStatus_FK
	WHERE PS.ProcessStatus <> 'Success' AND DATEADD(hh,@OrderCutoffHour,ProcessDate) > getDate()
	ORDER BY PD.ProcessDate ASC

	RETURN @NextExecutionDate

END
GO
