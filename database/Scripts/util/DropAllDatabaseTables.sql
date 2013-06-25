SET NOCOUNT ON;

DECLARE @name VARCHAR(128)
DECLARE @schema VARCHAR(128)
DECLARE @constraint VARCHAR(254)
DECLARE @SQL VARCHAR(254)

-----------------------------------
-- 01. DROP ALL FOREIGN KEYS
-----------------------------------
SELECT @name = (SELECT TOP 1 TABLE_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'FOREIGN KEY' ORDER BY TABLE_NAME) 
WHILE @name is not null 
BEGIN 
    SELECT @constraint = (SELECT TOP 1 CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'FOREIGN KEY' AND TABLE_NAME = @name ORDER BY CONSTRAINT_NAME) 
    WHILE @constraint IS NOT NULL 
    BEGIN 
        SELECT @SQL = 'ALTER TABLE [dbo].[' + RTRIM(@name) +'] DROP CONSTRAINT ' + RTRIM(@constraint) 
        EXEC (@SQL) 
        --PRINT 'Dropped FK Constraint: ' + @constraint + ' on ' + @name 
        SELECT @constraint = (SELECT TOP 1 CONSTRAINT_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'FOREIGN KEY' AND CONSTRAINT_NAME <> @constraint AND TABLE_NAME = @name ORDER BY CONSTRAINT_NAME) 
    END 
	SELECT @name = (SELECT TOP 1 TABLE_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE constraint_catalog=DB_NAME() AND CONSTRAINT_TYPE = 'FOREIGN KEY' ORDER BY TABLE_NAME) 
END 

-----------------------------------
-- 02. DROP ALL TABLES
-----------------------------------
SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] = 'U' ORDER BY [name])
SELECT @schema = (SELECT TOP 1 SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] = 'U' AND name = @name)
WHILE @name is not null
BEGIN
	SELECT @SQL = 'DROP TABLE [' + RTRIM(@schema) + '].[' + RTRIM(@name) +']'
	EXEC (@SQL)	 
	--PRINT 'Dropped Table: ' + @name
	SELECT @name = (SELECT TOP 1 [name] FROM sys.objects WHERE [type] = 'U' ORDER BY [name])
	SELECT @schema = (SELECT TOP 1 SCHEMA_NAME(schema_id) FROM sys.objects WHERE [type] = 'U' AND name = @name)
END

GO
