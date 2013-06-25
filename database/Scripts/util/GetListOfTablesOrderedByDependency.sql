SET NOCOUNT ON;

CREATE TABLE #Tables (
	TableName		varchar(100)		NOT NULL,
	TableID			decimal(18,0)		NOT NULL,
	OrderNumber		int					NOT NULL
)

CREATE TABLE #Dependencies (
	TableName		varchar(100)		NOT NULL,
	TableID			decimal(18,0)		NOT NULL,
	ParentTableName	varchar(100)		NOT NULL,
	ParentTableID	decimal(18,0)		NOT NULL
)

INSERT INTO #Tables SELECT name, object_id, 0 FROM sys.tables
WHERE LEFT(name,3) != 'sys'

INSERT INTO #Dependencies SELECT OBJECT_NAME(parent_object_id), parent_object_id, 
	OBJECT_NAME(referenced_object_id), referenced_object_id
FROM sys.foreign_keys

DECLARE @Continue bit, @LoopCount int; 
SET @Continue = 1; SET @LoopCount = 0
WHILE @Continue = 1
BEGIN
	SET @LoopCount = @LoopCount + 1
	IF EXISTS(SELECT * FROM #Dependencies D
		INNER JOIN #Tables T ON D.TableID = T.TableID
		INNER JOIN #Tables P ON D.ParentTableID = P.TableID
		WHERE T.OrderNumber >= P.OrderNumber
	)
	BEGIN
		UPDATE #Tables SET OrderNumber = OrderNumber + 1
		WHERE TableID IN
			(SELECT D.ParentTableID FROM #Dependencies D
			 INNER JOIN #Tables T ON D.TableID = T.TableID
			 INNER JOIN #Tables P ON D.ParentTableID = P.TableID
			 WHERE T.OrderNumber >= P.OrderNumber)
		AND TableID NOT IN
			(SELECT D.TableID FROM #Dependencies D
			 INNER JOIN #Tables T ON D.TableID = T.TableID
			 INNER JOIN #Tables P ON D.ParentTableID = P.TableID
			 WHERE T.OrderNumber >= P.OrderNumber)
	END
	ELSE
	BEGIN
		SET @Continue = 0
	END
	IF @LoopCount > 100 SET @Continue = 0
END

SELECT TableName FROM #Tables 
ORDER BY OrderNumber DESC, TableName ASC
