SET NOCOUNT ON;

IF NOT EXISTS (SELECT * FROM OrderStatus_Types WHERE Code = 'PARTIAL')
BEGIN

	DELETE FROM OrderStatus_Types WHERE Code = 'PENDING'
	DELETE FROM OrderStatus_Types WHERE Code = 'POSTED'
	INSERT INTO OrderStatus_Types (Code,Description) VALUES ('PARTIAL','Order Partially Filled')
	INSERT INTO OrderStatus_Types (Code,Description) VALUES ('FILLED','Order Filled')

END
GO


IF NOT EXISTS (SELECT * FROM TransactionStatus_Types)
BEGIN

	INSERT INTO TransactionStatus_Types (TransactionStatus, Description) VALUES ('PENDING', 'Pending Settlement')
	INSERT INTO TransactionStatus_Types (TransactionStatus, Description) VALUES ('SETTLED', 'Settled')
	INSERT INTO TransactionStatus_Types (TransactionStatus, Description) VALUES ('CANCELED', 'Canceled')
	
END
GO


IF NOT EXISTS (SELECT * FROM ProcessStatus_Types)
BEGIN	
	INSERT INTO ProcessStatus_Types (ProcessStatus, Description) VALUES ('Pending', 'Pending')
	INSERT INTO ProcessStatus_Types (ProcessStatus, Description) VALUES ('Running', 'Running')
	INSERT INTO ProcessStatus_Types (ProcessStatus, Description) VALUES ('Canceled', 'Canceled')
	INSERT INTO ProcessStatus_Types (ProcessStatus, Description) VALUES ('Failed', 'Failed')
	INSERT INTO ProcessStatus_Types (ProcessStatus, Description) VALUES ('Success', 'Success')
	INSERT INTO ProcessStatus_Types (ProcessStatus, Description) VALUES ('Undoing', 'Undoing')
END


IF NOT EXISTS (SELECT * FROM Process_Date)
BEGIN
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('Mar 28, 2013', 1, 0, getdate(), 0, '')
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('Apr 30, 2013', 1, 0, getdate(), 0, '')
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('May 31, 2013', 1, 0, getdate(), 0, '')
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('Jun 28, 2013', 1, 0, getdate(), 0, '')
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('Jul 31, 2013', 1, 0, getdate(), 0, '')
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('Aug 30, 2013', 1, 0, getdate(), 0, '')
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('Sep 30, 2013', 1, 0, getdate(), 0, '')
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('Oct 31, 2013', 1, 0, getdate(), 0, '')
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('Nov 29, 2013', 1, 0, getdate(), 0, '')
	INSERT INTO Process_Date (ProcessDate, ProcessStatus_FK, IsUndo, ModifiedDateTime, User_FK, Details) VALUES ('Dec 31, 2013', 1, 0, getdate(), 0, '')
END
GO


IF NOT EXISTS (SELECT * FROM Trading_Settings)
BEGIN
	INSERT INTO Trading_Settings (OrderCutoffHour, OrderCutoffMinute) VALUES (17, 0)
END



---------------------------------------------------------------------------------
-- App User / role settup
---------------------------------------------------------------------------------
DELETE AppUser_AppRole_Relationship
DELETE AppRole
SET IDENTITY_INSERT AppRole ON
go
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'AppRole')
BEGIN

	INSERT INTO AppRole (Role_PK, RoleName, RoleDescription)   VALUES (1, 'Trade',   'Can trade their user shares and view user oriented system documentation.' )
	INSERT INTO AppRole (Role_PK, RoleName, RoleDescription)   VALUES (2, 'Finance', 'Can execute and review trades.  Can settle executed trades' )
	INSERT INTO AppRole (Role_PK, RoleName, RoleDescription)   VALUES (3, 'Admin',   'Can update user status, reset passwords, add new users.  *Role not yet supported*' )
	INSERT INTO AppRole (Role_PK, RoleName, RoleDescription)   VALUES (4, 'Support', 'Can log in and see pages only.  *Role not yet supported*' )
END
go
SET IDENTITY_INSERT AppRole OFF
go

-----------------------------------------------------------------------------
-- Finance Accounts - Password16 original login
-----------------------------------------------------------------------------
IF NOT EXISTS (SELECT * from AppUser where UserName = 'Peter.Gensicke')
BEGIN
INSERT INTO [TBS_Trading].[dbo].[AppUser]
           ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked])
     VALUES('Peter.Gensicke', '6666076F822435624FABFE27F346F8806543865C', 0, 1, 0,'2013/03/25', 0,'2013/03/25','Peter.Gensicke', 0, 0, 0, 0, 0)
END  
IF NOT EXISTS (SELECT * from AppUser where UserName = 'Lynne.Harris')
BEGIN
INSERT INTO [TBS_Trading].[dbo].[AppUser]
           ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked])
     VALUES('Lynne.Harris', '6666076F822435624FABFE27F346F8806543865C', 0, 1, 0,'2013/03/25', 0,'2013/03/25','Lynne.Harris', 0, 0, 0, 0, 0)
END  
IF NOT EXISTS (SELECT * from AppUser where UserName = 'Trader.Test')
BEGIN
INSERT INTO [TBS_Trading].[dbo].[AppUser]
           ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked])
     VALUES('Trader.Test', '6666076F822435624FABFE27F346F8806543865C', 0, 1, 0,'2013/03/25', 0,'2013/03/25','Trader.Test', 0, 0, 0, 0, 0)
END  

--TRUNCATE TABLE AppUser_AppRole_Relationship
GO
DECLARE  @TRADE_ROLE decimal(18,0)
		,@FINANCE_ROLE	Decimal(18,0)
		SET @TRADE_ROLE		= (SELECT Role_PK FROM AppRole WHERE RoleName = 'Trade')
		SET @FINANCE_ROLE	= (SELECT Role_PK FROM AppRole WHERE RoleName = 'Finance')
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'AppUser_AppRole_Relationship')
BEGIN	
	INSERT INTO AppUser_AppRole_Relationship (User_FK, Role_FK) 
	SELECT User_PK, @TRADE_ROLE FROM AppUser
	WHERE UserName LIKE 'B%' OR UserName IN ('admin', 'Trader.Test')
	
	INSERT INTO AppUser_AppRole_Relationship (User_FK, Role_FK) 
	SELECT User_PK, @FINANCE_ROLE FROM AppUser
	WHERE UserName NOT LIKE 'B%' AND UserName NOT LIKE 'Trader.Test'
END
go

