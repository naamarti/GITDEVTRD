SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Marek Komza
-- Create date: 03/21/2013
-- Description:	
-- =============================================
CREATE FUNCTION [dbo].[f_GetCutoffTime] 
(
)
RETURNS datetime
AS
BEGIN
	DECLARE @CutoffString varchar(10)

	SELECT TOP 1 @CutoffString = CAST(OrderCutoffHour AS varchar(2)) + ':' + CAST(OrderCutoffMinute AS varchar(2))
	FROM Trading_Settings

	RETURN CAST(@CutoffString AS datetime)

END
GO
