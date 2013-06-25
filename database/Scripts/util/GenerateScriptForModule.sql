SET NOCOUNT ON; 

DECLARE 
	 @ModuleName	varchar(100)
	,@LF			char(2)

SET @ModuleName		= '$(OBJECT_NAME)'
SET @LF				= CHAR(13)+CHAR(10)

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

