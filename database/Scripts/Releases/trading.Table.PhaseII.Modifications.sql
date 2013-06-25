IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ContactInstitutionType_Contacts]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contacts]'))
	ALTER TABLE [dbo].[Contacts] DROP CONSTRAINT [FK_ContactInstitutionType_Contacts]

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Contacts_MailingAddresses]') AND parent_object_id = OBJECT_ID(N'[dbo].[Contacts]'))
	ALTER TABLE [dbo].[Contacts] DROP CONSTRAINT [FK_Contacts_MailingAddresses]

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_MailingAddresses_MailingCountries]') AND parent_object_id = OBJECT_ID(N'[dbo].[MailingAddresses]'))
	ALTER TABLE [dbo].[MailingAddresses] DROP CONSTRAINT [FK_MailingAddresses_MailingCountries]

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_MailingAddresses_MailingStates]') AND parent_object_id = OBJECT_ID(N'[dbo].[MailingAddresses]'))
	ALTER TABLE [dbo].[MailingAddresses] DROP CONSTRAINT [FK_MailingAddresses_MailingStates]

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AppNavigation_AppRole_Relationship_AppNavigation]') AND parent_object_id = OBJECT_ID(N'[dbo].[AppNavigation_AppRole_Relationship]'))
	ALTER TABLE [dbo].[AppNavigation_AppRole_Relationship] DROP CONSTRAINT [FK_AppNavigation_AppRole_Relationship_AppNavigation]

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AppNavigation_AppRole_Relationship_AppRole]') AND parent_object_id = OBJECT_ID(N'[dbo].[AppNavigation_AppRole_Relationship]'))
	ALTER TABLE [dbo].[AppNavigation_AppRole_Relationship] DROP CONSTRAINT [FK_AppNavigation_AppRole_Relationship_AppRole]

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_AppNavigation_AppNavigationGroup]') AND parent_object_id = OBJECT_ID(N'[dbo].[AppNavigation]'))
	ALTER TABLE [dbo].[AppNavigation] DROP CONSTRAINT [FK_AppNavigation_AppNavigationGroup]
GO

IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'TransactionExceptionType')				DROP TABLE TransactionExceptionType
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'Transaction_Types')						DROP TABLE Transaction_Types
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'MailingCountries')						DROP TABLE MailingCountries
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'MailingStates')							DROP TABLE MailingStates
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'MailingAddresses')						DROP TABLE MailingAddresses
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'EnvelopeTypes')							DROP TABLE EnvelopeTypes
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'ContactInstitutionType')					DROP TABLE ContactInstitutionType
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'Contacts')								DROP TABLE Contacts
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'AppNavigation')							DROP TABLE AppNavigation
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'AppNavigation_AppRole_Relationship')		DROP TABLE AppNavigation_AppRole_Relationship
IF EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'AppNavigationGroup')						DROP TABLE AppNavigationGroup
GO

IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Orders') AND name = 'Original_Quantity' ) 
BEGIN
	ALTER TABLE User_Orders
		ADD Original_Quantity decimal(18,2)
END
GO

IF EXISTS( SELECT * FROM User_Orders WHERE Original_Quantity IS NULL)
BEGIN
	UPDATE User_Orders SET
		Original_Quantity = Quantity
END
GO

IF EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Orders') AND name = 'Original_Quantity' ) 
BEGIN
	ALTER TABLE User_Orders
		ALTER COLUMN Original_Quantity decimal(18,2) NOT NULL
END
GO

--------------------------------------------------------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Balances') AND name = 'Pending_Quantity' ) 
BEGIN
	ALTER TABLE User_Balances
		ADD Pending_Quantity decimal(18,2)
END
GO

IF EXISTS( SELECT * FROM User_Balances WHERE Pending_Quantity IS NULL)
BEGIN
	UPDATE User_Balances SET
		Pending_Quantity = Quantity
END
GO

IF EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Balances') AND name = 'Pending_Quantity' ) 
BEGIN
	ALTER TABLE User_Balances
		ALTER COLUMN Pending_Quantity decimal(18,2) NOT NULL
END
GO


--------------------------------------------------------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Transactions') AND name = 'TransactionStatus_FK' ) 
BEGIN
	ALTER TABLE User_Transactions
		ADD TransactionStatus_FK decimal(18,0)
END
GO

IF EXISTS( SELECT * FROM User_Transactions WHERE TransactionStatus_FK IS NULL)
BEGIN
	UPDATE User_Transactions SET
		TransactionStatus_FK = 1
END
GO

IF EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Transactions') AND name = 'TransactionStatus_FK' ) 
BEGIN
	ALTER TABLE User_Transactions
		ALTER COLUMN TransactionStatus_FK decimal(18,0) NOT NULL
END
GO

--------------------------------------------------------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('Process_Date') AND name = 'Details' ) 
BEGIN
	ALTER TABLE Process_Date
		ADD Details varchar(500)
END
GO

IF EXISTS( SELECT * FROM Process_Date WHERE Details IS NULL)
BEGIN
	UPDATE Process_Date SET
		Details = ''
END
GO

IF EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('Process_Date') AND name = 'Details' ) 
BEGIN
	ALTER TABLE Process_Date
		ALTER COLUMN Details varchar(500) NOT NULL
END
GO

--------------------------------------------------------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Transactions') AND name = 'ProcessDate_FK' ) 
BEGIN
	ALTER TABLE User_Transactions
		ADD ProcessDate_FK decimal(18,0) NULL
END
GO

IF EXISTS( SELECT * FROM User_Transactions WHERE ProcessDate_FK IS NULL)
BEGIN
	UPDATE User_Transactions SET ProcessDate_FK = (SELECT ProcessDate_PK FROM Process_Date WHERE ProcessDate_PK = 1)
END
GO

IF EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Transactions') AND name = 'ProcessDate_FK' ) 
BEGIN
	ALTER TABLE User_Transactions
		ALTER COLUMN ProcessDate_FK decimal(18,0) NOT NULL
END
GO


IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID('FK_ProcessDateFK_ProcessDate') AND parent_object_id = OBJECT_ID('User_Transactions'))
BEGIN
	ALTER TABLE User_Transactions WITH CHECK 
		ADD CONSTRAINT [FK_ProcessDateFK_ProcessDate] FOREIGN KEY([ProcessDate_FK])
		REFERENCES [dbo].[Process_Date] ([ProcessDate_PK])
END
GO

--------------------------------------------------------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Transactions') AND name = 'Security_FK' ) 
BEGIN
	ALTER TABLE User_Transactions
		ADD Security_FK decimal(18,0) NULL
END
GO

IF EXISTS( SELECT * FROM User_Transactions WHERE Security_FK IS NULL)
BEGIN
	UPDATE User_Transactions SET Security_FK = O.Security_FK
	FROM User_Transactions T
	INNER JOIN User_Orders O ON O.Order_PK = T.Buy_Order_FK
END
GO

IF EXISTS (SELECT * FROM syscolumns WHERE id = OBJECT_ID('User_Transactions') AND name = 'Security_FK' ) 
BEGIN
	ALTER TABLE User_Transactions
		ALTER COLUMN Security_FK decimal(18,0) NOT NULL
END
GO

--------------------------------------------------------------------------------------------------------------------

