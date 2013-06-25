SET NOCOUNT ON;

DECLARE @name VARCHAR(128)
DECLARE @schema VARCHAR(128)
DECLARE @SQL VARCHAR(254)

-----------------------------------
-- 01. DROP ALL VIEWS
-----------------------------------
SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] = 'V' ORDER BY [name])
SELECT @schema = (SELECT SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] = 'V' AND name = @name)
WHILE @name is not null
BEGIN
	SELECT @SQL = 'DROP VIEW [' + RTRIM(@schema) + '].[' + RTRIM(@name) +']'
	EXEC (@SQL)	 
	--PRINT 'Dropped View: ' + @name
	SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] = 'V' ORDER BY [name])
	SELECT @schema = (SELECT SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] = 'V' AND name = @name)
END

-----------------------------------
-- 02. DROP ALL PROCEDURES
-----------------------------------
SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] = 'P' ORDER BY [name])
SELECT @schema = (SELECT SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] = 'P' AND name = @name)
WHILE @name is not null
BEGIN
	SELECT @SQL = 'DROP PROCEDURE [' + RTRIM(@schema) + '].[' + RTRIM(@name) +']'
	EXEC (@SQL)	 
	--PRINT 'Dropped Procedure: ' + @name
	SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] = 'P' ORDER BY [name])
	SELECT @schema = (SELECT SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] = 'P' AND name = @name)
END

-----------------------------------
-- 03. DROP ALL TRIGGERS
-----------------------------------
SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] = 'TR' ORDER BY [name])
SELECT @schema = (SELECT SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] = 'TR' AND name = @name)
WHILE @name is not null
BEGIN
	SELECT @SQL = 'DROP TRIGGER [' + RTRIM(@schema) + '].[' + RTRIM(@name) +']'
	EXEC (@SQL)	 
	--PRINT 'Dropped Trigger: ' + @name
	SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] = 'TR' ORDER BY [name])
	SELECT @schema = (SELECT SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] = 'TR' AND name = @name)
END

-----------------------------------
-- 04. DROP ALL FUNCTIONS
-----------------------------------
SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] IN ('FN', 'IF') ORDER BY [name])
SELECT @schema = (SELECT SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] IN ('FN', 'IF') AND name = @name)
WHILE @name is not null
BEGIN
	SELECT @SQL = 'DROP FUNCTION [' + RTRIM(@schema) + '].[' + RTRIM(@name) +']'
	EXEC (@SQL)	 
	--PRINT 'Dropped Function: ' + @name
	SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] IN ('FN', 'IF') ORDER BY [name])
	SELECT @schema = (SELECT SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] IN ('FN', 'IF') AND name = @name)
END

GO
