-----------------------------------------------------------------------------
-- initialize the accounts for the trading system
-----------------------------------------------------------------------------



IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID('FK_AppUser_Contacts') 
		AND parent_object_id = OBJECT_ID('AppUser'))
	ALTER TABLE AppUser 
	DROP CONSTRAINT FK_AppUser_Contacts
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID('FK_AppUser_AppUserType') 
		AND parent_object_id = OBJECT_ID('AppUser'))
	ALTER TABLE AppUser 
	DROP CONSTRAINT FK_AppUser_AppUserType
GO



-----------------------------------------------------------------------------
-- test accounts ( password = Password16 )
-----------------------------------------------------------------------------
IF NOT EXISTS (SELECT * from AppUser where UserName = 'admin')
BEGIN
INSERT INTO [TBS_Trading].[dbo].[AppUser]
           ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked])
     VALUES('admin', '6666076F822435624FABFE27F346F8806543865C', 0, 0, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0)
END  




-----------------------------------------------------------------------------
-- live accounts for the trading system
-----------------------------------------------------------------------------
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05309') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05309', '52EDBE46E3908A2336D7301E277D4B66A44C2F02', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05310') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05310', 'C5BEA0C4DD60CEB2BDF45DD5DC9304D215EB97B6', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05311') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05311', '056C6297A013D577FC9E3B677EF8389323981269', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05312') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05312', '056C6297A013D577FC9E3B677EF8389323981269', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05314') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05314', 'BB90ED60AB4EFDE16579FA47F1265C9CB79E76F8', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05315') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05315', 'C4AB8C66C50BF10B762E4389C39C503A26BD4455', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05316') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05316', 'E7F22D10FB3EEEBB7FFC8D5169C6AE1806E8A239', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05319') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05319', 'DA2F846ADBBEE9CA8B82CF3E36D283D8C9060BDD', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05320') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05320', '5B48D88A19941E3F151D49ECD72B30B650EA917A', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05321') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05321', '1C8B55A0F519F9904CF06C4BBF890C0E73F2F32A', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05322') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05322', '1C8B55A0F519F9904CF06C4BBF890C0E73F2F32A', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05323') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05323', '0C317B3828F09F3A66723BDA383461F5E698E153', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05325') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05325', 'AB49F7673A310014D59E742B3827D27EE0674F1C', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05326') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05326', '15233D689BD1AE03A666B5FFD83F5834EA5BD30D', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05327') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05327', '9DE33488CB4C45B2483AA4A6C42377535D1A1F62', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05328') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05328', '7FC9231234FFEAA7228D01F1128F2312B4D17983', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05329') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05329', 'B7BEA17843CD602B228FEF963983E3D18C884341', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05330') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05330', '74885532D6A5DF92FB13013E510B37C678333ACC', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05331') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05331', 'E1D8AF6F181950B3FD114E82CA8B8703F84EBFA9', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05332') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05332', '02BAA4869E65978EF694032F84A63AAD6B79D7C2', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05333') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05333', 'DFB373962DB08F76AA815A08C93A0A14FD33A9A4', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05334') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05334', 'E30861784EF1CABED9FA83F93382D9C96013ACE7', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05335') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05335', '18C448C7D0444BE4F5BA97A7CF7A81604658B9D8', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05336') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05336', '6B75384E207D46FF6D1B972A6B5B5DFA903831D1', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05337') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05337', '190B4404BB885E4726D13A9EBF39AE8C7B0D6D22', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05338') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05338', '1BBB8B7B99D2ACC79981A259ABE0A8C15BDC2239', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05339') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05339', '61693188783AEBEF64F6D1C2810A5D95F18B2BF1', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05340') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05340', 'F685E07A9567C6AB6A9206F7C2566531C413A6BD', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05341') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05341', '5B01D7DE5B5820C071031775AFC13C629DA27C51', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05342') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05342', 'B91F4502A07105CEBCE7E632807B55F7657F988D', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05343') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05343', '307ED7ABE34F490D4A1C374B2855FEE99EC5BAD8', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05344') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05344', '80C06016B31FFA3B0D157BEF344A5FE03CC7FD75', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05345') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05345', '9AA4E8F7F59D5175E0BADE010150EA61D05CB216', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05346') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05346', 'D88C6E8EDD9B24537966198C0E2539563C6C2DFE', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05347') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05347', 'DD6BDA495D2689298C7AE9CA01D940BE1A791AD0', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05348') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05348', 'C92F34C01E24476CD2D063505C71FA32574481B1', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05349') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05349', '97B0FD2F9B2C4E92174719458B283189C944E15B', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05350') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05350', 'A4311197A12C635910C29C21F0767D44734C79C2', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B05351') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B05351', '691E08F8456B71C8E6697909A848396352DA205C', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B06401') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B06401', '45A64E4E14CD7A38839C62B70BFF8B8A45C6DAA4', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B06402') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B06402', 'A03469112E55FC0F8F420AA53926537A0254E0E1', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B06403') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B06403', 'A83954A74FA4A3B76DC8AA4A43C5FCB445C3378F', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B06404') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B06404', '83E6D84BD1181D61C202289B2055BC6CFACD19A4', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B06405') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B06405', '806E0B8E0977F7FCBB580D20B2F9726DA74BAB12', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B06406') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B06406', '60FE82D4C44B3BB12EA6EB40867C38B9EDC37CB4', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END
IF NOT EXISTS (SELECT * from TBS_Trading..AppUser where UserName = 'B06407') BEGIN INSERT INTO [TBS_Trading].[dbo].[AppUser] ([UserName],[UserPassword],[Contact_FK],[IsPasswordReset],[IsSecretQuestionsSet],[PasswordChangeDateTime],[LoginFailedAttempts],[LastModifiedDateTime],[LastModifiedByUserName],[IsDeleted],[VersionNumber],[UserType_FK],[IsMandatoryContactInfoSet],[IsLocked]) VALUES('B06407', 'A09F29CC98D27721CDF284E6D5CFD0B93C146491', 0, 1, 0,'2013/03/01', 0,'2013/03/01','admin', 0, 0, 0, 0, 0) END


	


GO

