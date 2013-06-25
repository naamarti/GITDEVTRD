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
CREATE PROCEDURE [dbo].[p_j_GenerateScriptForTable]
	@TableName varchar(100)
AS
BEGIN

SET NOCOUNT ON; 

DECLARE 
	 @LF			char(2)
	,@TAB			char(1)
	,@MainSQL		varchar(MAX)
	,@CurrentColID	int
	,@CurrentIndex	int
	,@ParentCols	varchar(300)
	,@RefCols		varchar(300)

SET @LF				= CHAR(13)+CHAR(10)
SET @TAB			= CHAR(9)

CREATE TABLE #Columns (
	ColumnID		int IDENTITY(1,1)	NOT NULL,
	ColumnName		varchar(128)		NOT NULL,
	DataType		varchar(128)		NOT NULL,
	MaxLength		int					NOT NULL,
	Precision		int					NOT NULL,
	Scale			int					NOT NULL,
	IsNullable		bit					NOT NULL,
	IsIdentity		bit					NOT NULL,
	DefaultName		varchar(128)		NOT NULL,
	DefaultDef		varchar(max)		NOT NULL,
	IsShowLength	bit					NOT NULL,
	IsFinalColumn	bit					NOT NULL,
	IsCommaAfter	bit					NOT NULL,
)

CREATE TABLE #Indexes (
	IndexID			int	IDENTITY(1,1)	NOT NULL,
	IndexName		varchar(128)		NOT NULL,
	IsPrimaryKey	bit					NOT NULL,
	IsUniqueKey		bit					NOT NULL,
	IsUnique		bit					NOT NULL,
	IsClustered		bit					NOT NULL,
	IsFinalIndex	bit					NOT NULL
)

CREATE TABLE #IndexColumns (
	IndexID			int					NOT NULL,
	ColumnID		int					NOT NULL,
	ColumnName		varchar(128)		NOT NULL,
	IsDesc			bit					NOT NULL,
	IsFinalColumn	bit					NOT NULL
)

CREATE TABLE #ForeignKeys (
	KeyID			int	IDENTITY(1,1)	NOT NULL,
	KeyName			varchar(128)		NOT NULL,
	ParentTable		varchar(128)		NOT NULL,
	ReferencedTable	varchar(128)		NOT NULL
)

CREATE TABLE #ForeignKeyColumns (
	KeyID			int					NOT NULL,
	ColumnID		int					NOT NULL,
	ParentCol		varchar(128)		NOT NULL,
	ReferencedCol	varchar(128)		NOT NULL,
	IsFinalColumn	bit					NOT NULL
)

INSERT INTO #Columns (ColumnName, DataType, MaxLength, Precision, Scale, IsNullable, IsIdentity, 
	DefaultName, DefaultDef, IsShowLength, IsFinalColumn, IsCommaAfter)
SELECT
	 c.name
	,t.name
	,CASE UPPER (t.name) WHEN 'NCHAR' THEN c.Max_Length / 2 WHEN 'NVARCHAR' THEN c.Max_Length / 2 ELSE c.Max_Length END
	,c.Precision
	,c.Scale
	,c.is_nullable
	,c.is_identity
	,ISNULL(CASE d.is_system_named WHEN 1 THEN '' ELSE d.name END, '')
	,ISNULL(d.definition, '')
	,CASE UPPER(t.name) WHEN 'CHAR' THEN 1 WHEN 'VARCHAR' THEN 1 WHEN 'NCHAR' THEN 1 WHEN 'NVARCHAR' THEN 1 WHEN 'VARBINARY' THEN 1 ELSE 0 END
	,0
	,1
FROM sys.objects o
INNER JOIN sys.columns c ON o.object_id = c.object_id
INNER JOIN sys.types t ON c.user_type_id = t.user_type_id
LEFT JOIN sys.default_constraints d ON d.object_id = c.default_object_id
WHERE o.type = 'U' and o.name = @TableName

INSERT INTO #Indexes (IndexName, IsPrimaryKey, IsUniqueKey, IsUnique, IsClustered, IsFinalIndex)
SELECT
	 I.name
	,I.is_primary_key
	,I.is_unique_constraint
	,I.is_unique
	,CASE I.type WHEN 1 THEN 1 ELSE 0 END
	,0
FROM sys.indexes I
INNER JOIN sys.index_columns IC ON IC.object_id = I.object_id AND IC.index_id = I.index_id
WHERE I.object_id = OBJECT_ID(@TableName)
GROUP BY I.is_primary_key, I.is_unique_constraint, I.name, I.is_unique, I.type
ORDER BY I.is_primary_key DESC, I.is_unique_constraint DESC, I.name

INSERT INTO #IndexColumns (IndexID, ColumnID, ColumnName, IsDesc, IsFinalColumn)
SELECT
	 #Indexes.IndexID
	,IC.index_column_id
	,C.name
	,IC.is_descending_key
	,0
FROM sys.index_columns IC
INNER JOIN sys.indexes I ON IC.object_id = I.object_id AND IC.index_id = I.index_id
INNER JOIN #Indexes ON I.name = #Indexes.IndexName
INNER JOIN sys.columns C ON IC.object_id = C.object_id AND IC.column_id = C.column_id
WHERE IC.object_id = OBJECT_ID(@TableName)

UPDATE #Indexes SET IsFinalIndex = 1
FROM #Indexes I
WHERE (IsPrimaryKey = 1 OR IsUniqueKey = 1)
AND NOT EXISTS (SELECT * FROM #Indexes I2 WHERE (I2.IsPrimaryKey = 1 OR I2.IsUniqueKey = 1) AND I2.IndexID > I.IndexID)

UPDATE #IndexColumns SET IsFinalColumn = 1
FROM #IndexColumns I
WHERE NOT EXISTS (SELECT * FROM #IndexColumns I2 WHERE I2.IndexID = I.IndexID AND I2.ColumnID > I.ColumnID)

UPDATE #Columns SET IsFinalColumn = 1
FROM #Columns C
WHERE NOT EXISTS (SELECT * FROM #Columns C2 WHERE C2.ColumnID > C.ColumnID)

UPDATE #Columns SET IsCommaAfter = 0
WHERE IsFinalColumn = 1
AND NOT EXISTS (SELECT * FROM #Indexes WHERE IsPrimaryKey = 1 OR IsUniqueKey = 1)

INSERT INTO #ForeignKeys (KeyName, ParentTable, ReferencedTable)
SELECT 
	 FK.name
	,OBJECT_NAME(FC.parent_object_id)
	,OBJECT_NAME(FC.referenced_object_id)
FROM Sys.foreign_keys FK
INNER JOIN sys.foreign_key_columns FC ON FK.object_id = FC.constraint_object_id
WHERE FK.parent_object_id = OBJECT_ID(@TableName)
GROUP BY FK.name, OBJECT_NAME(FC.parent_object_id), OBJECT_NAME(FC.referenced_object_id)
ORDER BY FK.name

INSERT INTO #ForeignKeyColumns (KeyID, ColumnID, ParentCol, ReferencedCol, IsFinalColumn)
SELECT
	 #ForeignKeys.KeyID
	,FC.constraint_column_id
	,PC.name
	,RC.name
	,0
FROM sys.foreign_key_columns FC
INNER JOIN Sys.foreign_keys FK ON FK.object_id = FC.constraint_object_id
INNER JOIN #ForeignKeys ON #ForeignKeys.KeyName = FK.name
INNER JOIN sys.columns PC ON FC.parent_object_id = PC.object_id AND FC.parent_column_id = PC.column_id
INNER JOIN sys.columns RC ON FC.referenced_object_id = RC.object_id AND FC.referenced_column_id = RC.column_id
WHERE FK.parent_object_id = OBJECT_ID(@TableName)

UPDATE #ForeignKeyColumns SET IsFinalColumn = 1
FROM #ForeignKeyColumns F
WHERE NOT EXISTS (SELECT * FROM #ForeignKeyColumns F2 WHERE F2.KeyID = F.KeyID AND F2.ColumnID > F.ColumnID)

--------------------------------------------------------------------
-- Script for Create Table Header
--------------------------------------------------------------------
SET @MainSQL = 
	 'SET ANSI_NULLS ON' 
	+ @LF
	+ 'GO' 
	+ @LF
	+ 'SET QUOTED_IDENTIFIER ON' 
	+ @LF
	+ 'GO' 
	+ @LF
	+ 'SET ANSI_PADDING ON' 
	+ @LF	
	+ 'GO' 
	+ @LF
	+ 'CREATE TABLE [dbo].[' + @TableName + '](' 
	+ @LF

--------------------------------------------------------------------
-- Script for each Column
--------------------------------------------------------------------
SET @CurrentColID = (SELECT MIN(ColumnID) FROM #Columns)
WHILE @CurrentColID <= (SELECT MAX(ColumnID) FROM #Columns)
BEGIN
	SET @MainSQL = @MainSQL +
	(SELECT 
		@TAB
		+ '[' + ColumnName + '] '
		+ '[' + DataType + ']'
		+ CASE UPPER(DataType) WHEN 'DECIMAL' THEN '(' + CONVERT(varchar,Precision) + ', ' + CONVERT(varchar,Scale) + ')' ELSE '' END
		+ CASE IsShowLength WHEN 1 THEN '(' + CASE MaxLength WHEN -1 THEN 'MAX' ELSE CONVERT(varchar,MaxLength) END + ')' ELSE '' END
		+ CASE IsIdentity WHEN 1 THEN ' IDENTITY(1,1)' ELSE '' END
		+ ' '
		+ CASE IsNullable WHEN 1 THEN 'NULL' ELSE 'NOT NULL' END		
		+ CASE DefaultName WHEN '' THEN '' ELSE ' CONSTRAINT [' + DefaultName + '] ' END
		+ CASE DefaultDef WHEN '' THEN '' ELSE ' DEFAULT ' + DefaultDef END
		+ CASE IsCommaAfter WHEN 1 THEN ',' ELSE '' END
		+ @LF
	FROM #Columns WHERE ColumnID = @CurrentColID
	)
	SET @CurrentColID = @CurrentColID + 1
END

--------------------------------------------------------------------
-- Script for Primary Key
--------------------------------------------------------------------
IF EXISTS (SELECT * FROM #Indexes WHERE IsPrimaryKey = 1)
BEGIN
	SET @CurrentIndex = (SELECT IndexID FROM #Indexes WHERE IsPrimaryKey = 1)
	SET @MainSQL = @MainSQL +
	(SELECT
	' CONSTRAINT [' + IndexName + '] PRIMARY KEY '
	+ CASE IsClustered WHEN 1 THEN 'CLUSTERED ' ELSE 'NONCLUSTERED ' END
	+ @LF
	+ '('
	+ @LF
	FROM #Indexes WHERE IndexID = @CurrentIndex
	)

	SET @CurrentColID = (SELECT MIN(ColumnID) FROM #IndexColumns WHERE IndexID = @CurrentIndex)
	WHILE @CurrentColID <= (SELECT MAX(ColumnID) FROM #IndexColumns WHERE IndexID = @CurrentIndex)
	BEGIN
		SET @MainSQL = @MainSQL +
		(SELECT
		+ @TAB + '[' + ColumnName + '] '
		+ CASE IsDesc WHEN 1 THEN 'DESC' ELSE 'ASC' END
		+ CASE IsFinalColumn WHEN 1 THEN '' ELSE ',' END
		+ @LF
		FROM #IndexColumns WHERE IndexID = @CurrentIndex AND ColumnID = @CurrentColID
		)
		SET @CurrentColID = @CurrentColID + 1
	END		

	SET @MainSQL = @MainSQL +
	(SELECT
	+ ')WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]'
	+ CASE IsFinalIndex WHEN 1 THEN '' ELSE ',' END
	+ @LF
	FROM #Indexes WHERE IndexID = @CurrentIndex
	)
END

--------------------------------------------------------------------
-- Script for Unique Keys
--------------------------------------------------------------------
SET @CurrentIndex = (SELECT MIN(IndexID) FROM #Indexes WHERE IsUniqueKey = 1)
WHILE @CurrentIndex <= (SELECT MAX(IndexID) FROM #Indexes WHERE IsUniqueKey = 1)
BEGIN
	SET @MainSQL = @MainSQL +
		(SELECT
		' CONSTRAINT [' + IndexName + '] UNIQUE '
		+ CASE IsClustered WHEN 1 THEN 'CLUSTERED ' ELSE 'NONCLUSTERED ' END
		+ @LF
		+ '('
		+ @LF
		FROM #Indexes WHERE IndexID = @CurrentIndex
		)
	
	SET @CurrentColID = (SELECT MIN(ColumnID) FROM #IndexColumns WHERE IndexID = @CurrentIndex)
	WHILE @CurrentColID <= (SELECT MAX(ColumnID) FROM #IndexColumns WHERE IndexID = @CurrentIndex)
	BEGIN
		SET @MainSQL = @MainSQL +		
			(SELECT
			@TAB 
			+ '[' + ColumnName + '] '
			+ CASE IsDesc WHEN 1 THEN 'DESC' ELSE 'ASC' END
			+ CASE IsFinalColumn WHEN 1 THEN '' ELSE ',' END
			+ @LF
			FROM #IndexColumns WHERE IndexID = @CurrentIndex AND ColumnID = @CurrentColID
			)
		SET @CurrentColID = @CurrentColID + 1
	END		

	SET @MainSQL = @MainSQL +
		(SELECT
		')WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]'
		+ CASE IsFinalIndex WHEN 1 THEN '' ELSE ',' END
		+ @LF
		FROM #Indexes WHERE IndexID = @CurrentIndex
		)
	
	SET @CurrentIndex = @CurrentIndex + 1
END

--------------------------------------------------------------------
-- Script for Create Table Footer
--------------------------------------------------------------------
SET @MainSQL = @MainSQL +
	') ON [PRIMARY]'
	+ @LF
	+ 'GO'
	+ @LF
	+ 'SET ANSI_PADDING OFF'
	+ @LF
	+ 'GO'

--------------------------------------------------------------------
-- Script for Indexes
--------------------------------------------------------------------
SET @CurrentIndex = (SELECT MIN(IndexID) FROM #Indexes WHERE IsPrimaryKey = 0 AND IsUniqueKey = 0)
WHILE @CurrentIndex <= (SELECT MAX(IndexID) FROM #Indexes WHERE IsPrimaryKey = 0 AND IsUniqueKey = 0)
BEGIN
	SET @MainSQL = @MainSQL +
		(SELECT
		 @LF
		+ 'CREATE '
		+ CASE IsUnique WHEN 1 THEN 'UNIQUE ' ELSE '' END
		+ CASE IsClustered WHEN 1 THEN 'CLUSTERED ' ELSE 'NONCLUSTERED ' END
		+ 'INDEX [' + IndexName + '] ON [dbo].[' + @TableName + ']'
		+ @LF
		+ '('
		+ @LF
		FROM #Indexes WHERE IndexID = @CurrentIndex
		)
	
	SET @CurrentColID = (SELECT MIN(ColumnID) FROM #IndexColumns WHERE IndexID = @CurrentIndex)
	WHILE @CurrentColID <= (SELECT MAX(ColumnID) FROM #IndexColumns WHERE IndexID = @CurrentIndex)
	BEGIN
		SET @MainSQL = @MainSQL +		
			(SELECT
			@TAB 
			+ '[' + ColumnName + '] '
			+ CASE IsDesc WHEN 1 THEN 'DESC' ELSE 'ASC' END
			+ CASE IsFinalColumn WHEN 1 THEN '' ELSE ',' END
			+ @LF
			FROM #IndexColumns WHERE IndexID = @CurrentIndex AND ColumnID = @CurrentColID
			)
		SET @CurrentColID = @CurrentColID + 1
	END		

	SET @MainSQL = @MainSQL +
		(SELECT
		')WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]'
		+ @LF
		+ 'GO'
		FROM #Indexes WHERE IndexID = @CurrentIndex
		)
	
	SET @CurrentIndex = @CurrentIndex + 1
END
	
--------------------------------------------------------------------
-- Script for Foreign Keys
--------------------------------------------------------------------
SET @CurrentIndex = (SELECT MIN(KeyID) FROM #ForeignKeys)
WHILE @CurrentIndex <= (SELECT MAX(KeyID) FROM #ForeignKeys)
BEGIN
	SET @ParentCols = ''
	SET @RefCols = ''
	SET @CurrentColID = (SELECT MIN(ColumnID) FROM #ForeignKeyColumns WHERE KeyID = @CurrentIndex)
	WHILE @CurrentColID <= (SELECT MAX(ColumnID) FROM #ForeignKeyColumns WHERE KeyID = @CurrentIndex)
	BEGIN
		SET @ParentCols = @ParentCols +
			(SELECT
			+ '[' + ParentCol + ']'
			+ CASE IsFinalColumn WHEN 1 THEN '' ELSE ', ' END
			FROM #ForeignKeyColumns WHERE KeyID = @CurrentIndex AND ColumnID = @CurrentColID
			)
		SET @RefCols = @RefCols  +
			(SELECT
			+ '[' + ReferencedCol + ']'
			+ CASE IsFinalColumn WHEN 1 THEN '' ELSE ', ' END
			FROM #ForeignKeyColumns WHERE KeyID = @CurrentIndex AND ColumnID = @CurrentColID
			)
		SET @CurrentColID = @CurrentColID + 1
	END		

	SET @MainSQL = @MainSQL +
		(SELECT
		@LF
		+ 'ALTER TABLE [dbo].[' + ParentTable + ']  WITH CHECK ADD  CONSTRAINT [' + KeyName + '] FOREIGN KEY(' + @ParentCols + ')'
		+ @LF
		+ 'REFERENCES [dbo].[' + ReferencedTable + '] (' + @RefCols + ')'
		+ @LF
		+ 'GO'
		+ @LF
		+ 'ALTER TABLE [dbo].[' + ParentTable + '] CHECK CONSTRAINT [' + KeyName + ']'
		+ @LF
		+ 'GO'
		FROM #ForeignKeys WHERE KeyID = @CurrentIndex
		)
	
	SET @CurrentIndex = @CurrentIndex + 1
END

SELECT RTRIM(@MainSQL)

END
GO
