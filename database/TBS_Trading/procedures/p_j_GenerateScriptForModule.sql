SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Val Catrini
-- Create date: APR 2, 2010
-- Modified: 	
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[p_j_GenerateScriptForModule]
	@ModuleName varchar(100)
AS
BEGIN

SET NOCOUNT ON; 
DECLARE @LF char(2)
SET @LF=CHAR(13)+CHAR(10)

SELECT 
	'SET ANSI_NULLS '
	+ CASE uses_ansi_nulls WHEN 1 THEN 'ON' ELSE 'OFF' END
	+ @LF 
	+ 'GO' 
	+ @LF 
	+ 'SET QUOTED_IDENTIFIER ' 
	+ CASE uses_quoted_identifier WHEN 1 THEN 'ON' ELSE 'OFF' END
	+ @LF 
	+ 'GO' 
	+ @LF
	+ definition 
	+ 'GO' 
FROM sys.sql_modules 
WHERE object_id = OBJECT_ID(@ModuleName);

END
GO
