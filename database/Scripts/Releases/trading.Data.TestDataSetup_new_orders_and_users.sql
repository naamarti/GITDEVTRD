SET NOCOUNT ON;

---------------------------------------------------------------------
SET NOCOUNT ON;

---------------------------------------------------------------------
-- AppConfiguration
---------------------------------------------------------------------
--IF NOT EXISTS (SELECT * FROM AppConfiguration)
--	INSERT INTO [TBS_Trading].[dbo].[AppConfiguration]
--	           ([DefaultUserPassword]
--	           ,[MinUsernameLength]
--	           ,[MinChallengeQuestions]
--	           ,[RandomChallengeQuestions]
--	           ,[SessionTimeoutMinutes]
--	           ,[WebLogging]
--	           ,[EnvironmentName]
--	           ,[PasswordMinLength]
--	           ,[PasswordValidPeriodInDays]
--	           ,[PasswordNumCaps]
--	           ,[PasswordNumNumeric]
--	           ,[PasswordNumHistoricRestrict]
--	           ,[IsMandatoryContactInfoEnabled]
--	           ,[NumLoginAttemptsBeforeLockout]
--	           ,[VersionNumber]
--	           ,[ReconLockOutDays]
--	           ,[JavaVersionNumber]
--	           ,[DotNetVersionNumber]
--	           ,[DBVersionNumber]
--	           ,[IsTBSInternalTestMode])
--	     VALUES
--	           ('T0talBank1'
--	           ,6
--	           ,5
--	           ,1
--	           ,60
--	           ,1
--	           ,'LOCAL'
--	           ,6
--	           ,90
--	           ,1
--	           ,1
--	           ,6
--	           ,0
--	           ,3
--	           ,1
--	           ,1
--	           ,'2012.12.13'
--	           ,''
--	           ,'2012.12.13'
--	           ,0 )
--GO

---------------------------------------------------------------------
-- ChallengeQuestion
---------------------------------------------------------------------
--IF NOT EXISTS (SELECT * FROM ChallengeQuestion)
--BEGIN
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What are the last 4 digits of your social security number'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What is the name of the city you were born in'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What is the name of your favorite pet'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What is your driver''s license number'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What is your father''s middle name'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What is your maternal grandfather''s first name'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What is your maternal grandfather''s occupation'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What is your mother''s maiden name'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What is your mother''s middle name'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What was the make and model of your first car'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What was the name of your childhood best friend'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What was the name of your college best friend'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What was the name of your favorite teacher'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What was your first home address'
--	insert into ChallengeQuestion (ChallengeQuestion) select 'What was your first telephone number'
--END
--GO
-- Mailing Countries
---------------------------------------------------------------------
IF NOT EXISTS (SELECT * FROM MailingCountries)
	INSERT INTO MailingCountries (Country, PostalCode) VALUES ('United States', 'US')
GO

---------------------------------------------------------------------
-- Mailing States
---------------------------------------------------------------------
IF NOT EXISTS (SELECT * FROM MailingStates)
	INSERT INTO MailingStates (State, PostalCode ) VALUES ( 'New Jersey', 'NJ' )
GO

---------------------------------------------------------------------
-- Mailing Address
---------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM MailingAddresses)
BEGIN

	DECLARE 
		@NJ		decimal(18,0),
		@USA	decimal(18,0)
	
	SET @NJ		= ( SELECT State_PK FROM MailingStates WHERE PostalCode = 'NJ' )
	SET @USA	= ( SELECT Country_PK FROM MailingCountries WHERE PostalCode = 'US' )
	
	INSERT INTO [TBS_Trading].[dbo].[MailingAddresses]
	           ([Line1]
	           ,[Line2]
	           ,[Line3]
	           ,[Line4]
	           ,[City]
	           ,[State_FK]
	           ,[ZipCode]
	           ,[Country_FK])
	     VALUES(
	     		'3 University Plaza',
	     		'',
	     		'',
	     		'',
	     		'Hackensack',
	     		@NJ,
	     		'07603',
	     		@USA
	     	)

END
GO

---------------------------------------------------------------------
-- Contacts
---------------------------------------------------------------------
	DECLARE 
		@DEMO_ADDRESS 		decimal(18,0),
		@CONTACT_TYPE_SI	decimal(18,0)
	
	SET @DEMO_ADDRESS 		= ( SELECT MailingAddress_PK FROM MailingAddresses WHERE Line1 = '3 University Plaza' )
	SET @CONTACT_TYPE_SI	= ( SELECT ContactType_PK FROM ContactInstitutionType WHERE ContactInstitutionType = 'Source Institution' )
	
IF NOT EXISTS (SELECT * FROM Contacts WHERE FullName = 'demo user')
BEGIN
	INSERT INTO [TBS_Trading].[dbo].[Contacts]
	           ([ContactType]
	           ,[FullName]
	           ,[NameTitle]
	           ,[FirstName]
	           ,[MiddleName]
	           ,[LastName]
	           ,[NameSuffix]
	           ,[CompanyName]
	           ,[DepartmentName]
	           ,[JobTitle]
	           ,[BusinessPhone]
	           ,[BusinessFax]
	           ,[MobilePhone]
	           ,[EmailAddress]
	           ,[MailingAddress_FK]
	           ,[Comments]
	           ,[LastModifiedDateTime]
	           ,[LastModifiedByUserName]
	           ,[IsDeleted]
	           ,[VersionNumber]
	           ,[ContactInstitutionType_FK])
	     VALUES (
	     		'demo user',
	     		'demo user',
	     		'Mr.',
	     		'demo',
	     		'mn',
	     		'user',
	     		'',
	     		'Total Bank Solutions',
	     		'Systems',
	     		'',
	     		'973-555-5555',
	     		'973-555-1234',
	     		'973-123-5665',
	     		'demo@totalbanksolutions.com',
	     		@DEMO_ADDRESS,
				'',
				getDate(),
				'Systems',
				0,
				1,
				@CONTACT_TYPE_SI	
			)
END
IF NOT EXISTS (SELECT * FROM Contacts WHERE FullName = 'demo user2')
BEGIN			
		INSERT INTO [TBS_Trading].[dbo].[Contacts]
	           ([ContactType]
	           ,[FullName]
	           ,[NameTitle]
	           ,[FirstName]
	           ,[MiddleName]
	           ,[LastName]
	           ,[NameSuffix]
	           ,[CompanyName]
	           ,[DepartmentName]
	           ,[JobTitle]
	           ,[BusinessPhone]
	           ,[BusinessFax]
	           ,[MobilePhone]
	           ,[EmailAddress]
	           ,[MailingAddress_FK]
	           ,[Comments]
	           ,[LastModifiedDateTime]
	           ,[LastModifiedByUserName]
	           ,[IsDeleted]
	           ,[VersionNumber]
	           ,[ContactInstitutionType_FK])
	     VALUES (
	     		'demo user2',
	     		'demo user2',
	     		'Mr.',
	     		'demo2',
	     		'mn',
	     		'user2',
	     		'',
	     		'Total Bank Solutions',
	     		'Systems',
	     		'',
	     		'973-555-5555',
	     		'973-555-1234',
	     		'973-123-5665',
	     		'demo@totalbanksolutions.com',
	     		@DEMO_ADDRESS,
				'',
				getDate(),
				'Systems',
				0,
				1,
				@CONTACT_TYPE_SI	
			)
END
IF NOT EXISTS (SELECT * FROM Contacts WHERE FullName = 'demo user3')
BEGIN
		INSERT INTO [TBS_Trading].[dbo].[Contacts]
	           ([ContactType]
	           ,[FullName]
	           ,[NameTitle]
	           ,[FirstName]
	           ,[MiddleName]
	           ,[LastName]
	           ,[NameSuffix]
	           ,[CompanyName]
	           ,[DepartmentName]
	           ,[JobTitle]
	           ,[BusinessPhone]
	           ,[BusinessFax]
	           ,[MobilePhone]
	           ,[EmailAddress]
	           ,[MailingAddress_FK]
	           ,[Comments]
	           ,[LastModifiedDateTime]
	           ,[LastModifiedByUserName]
	           ,[IsDeleted]
	           ,[VersionNumber]
	           ,[ContactInstitutionType_FK])
	     VALUES (
	     		'demo user3',
	     		'demo user3',
	     		'Mr.',
	     		'demo3',
	     		'mn',
	     		'user3',
	     		'',
	     		'Total Bank Solutions',
	     		'Systems',
	     		'',
	     		'973-555-5555',
	     		'973-555-1234',
	     		'973-123-5665',
	     		'demo@totalbanksolutions.com',
	     		@DEMO_ADDRESS,
				'',
				getDate(),
				'Systems',
				0,
				1,
				@CONTACT_TYPE_SI	
			)			

END
IF NOT EXISTS (SELECT * FROM Contacts WHERE FullName = 'demo user4')
BEGIN
		INSERT INTO [TBS_Trading].[dbo].[Contacts]
	           ([ContactType]
	           ,[FullName]
	           ,[NameTitle]
	           ,[FirstName]
	           ,[MiddleName]
	           ,[LastName]
	           ,[NameSuffix]
	           ,[CompanyName]
	           ,[DepartmentName]
	           ,[JobTitle]
	           ,[BusinessPhone]
	           ,[BusinessFax]
	           ,[MobilePhone]
	           ,[EmailAddress]
	           ,[MailingAddress_FK]
	           ,[Comments]
	           ,[LastModifiedDateTime]
	           ,[LastModifiedByUserName]
	           ,[IsDeleted]
	           ,[VersionNumber]
	           ,[ContactInstitutionType_FK])
	     VALUES (
	     		'demo user4',
	     		'demo user4',
	     		'Mr.',
	     		'demo4',
	     		'mn',
	     		'user4',
	     		'',
	     		'Total Bank Solutions',
	     		'Systems',
	     		'',
	     		'973-555-5555',
	     		'973-555-1234',
	     		'973-123-5665',
	     		'demo@totalbanksolutions.com',
	     		@DEMO_ADDRESS,
				'',
				getDate(),
				'Systems',
				0,
				1,
				@CONTACT_TYPE_SI	
			)						
END
GO


IF NOT EXISTS (SELECT * FROM AppUserType)
BEGIN
     		
    INSERT INTO AppUserType (
            [UserType]
           ,[Description])
     VALUES (
            'TBS'
           ,'TBS User'
           )
END	   
GO
     		
     		
---------------------------------------------------------------------
-- AppUser
---------------------------------------------------------------------
	DECLARE 
		@CONTACT_DEMO	decimal(18,0),
		@TBS_USER_TYPE	decimal(18,0)

	SET @TBS_USER_TYPE	= ( SELECT UserType_PK FROM AppUserType WHERE UserType = 'TBS' )
	
SET @CONTACT_DEMO	= ( SELECT Contact_PK FROM Contacts WHERE FullName = 'demo user' )
IF NOT EXISTS (SELECT * FROM AppUser WHERE Contact_FK = @CONTACT_DEMO)
BEGIN	
	INSERT INTO AppUser (
				[UserName]
	           ,[UserPassword]
	           ,[Contact_FK]
	           ,[IsPasswordReset]
	           ,[IsSecretQuestionsSet]
	           ,[PasswordChangeDateTime]
	           ,[LoginFailedAttempts]
	           ,[LastModifiedDateTime]
	           ,[LastModifiedByUserName]
	           ,[IsDeleted]
	           ,[VersionNumber]
	           ,[UserType_FK]
	           ,[IsMandatoryContactInfoSet]
	           ,[IsLocked])
	     VALUES ( 
	     		'demo',
	     		'demo',
	     		@CONTACT_DEMO,
	     		0,
	     		1,
	     		getDate(),
	     		0,
	     		getDate(),
	     		'Systems',
	     		0,
	     		1,
	     		@TBS_USER_TYPE,
	     		1,
	     		0
	     	)
END
	     	
SET @CONTACT_DEMO	= ( SELECT Contact_PK FROM Contacts WHERE FullName = 'demo user2' )
IF NOT EXISTS (SELECT * FROM AppUser WHERE Contact_FK = @CONTACT_DEMO)
BEGIN		     	
	INSERT INTO AppUser (
				[UserName]
	           ,[UserPassword]
	           ,[Contact_FK]
	           ,[IsPasswordReset]
	           ,[IsSecretQuestionsSet]
	           ,[PasswordChangeDateTime]
	           ,[LoginFailedAttempts]
	           ,[LastModifiedDateTime]
	           ,[LastModifiedByUserName]
	           ,[IsDeleted]
	           ,[VersionNumber]
	           ,[UserType_FK]
	           ,[IsMandatoryContactInfoSet]
	           ,[IsLocked])
	     VALUES ( 
	     		'demo2',
	     		'demo2',
	     		@CONTACT_DEMO,
	     		0,
	     		1,
	     		getDate(),
	     		0,
	     		getDate(),
	     		'Systems',
	     		0,
	     		1,
	     		@TBS_USER_TYPE,
	     		1,
	     		0
	     	)
END
	     	
SET @CONTACT_DEMO	= ( SELECT Contact_PK FROM Contacts WHERE FullName = 'demo user3' )
IF NOT EXISTS (SELECT * FROM AppUser WHERE Contact_FK = @CONTACT_DEMO)
BEGIN		     	
	INSERT INTO AppUser (
				[UserName]
	           ,[UserPassword]
	           ,[Contact_FK]
	           ,[IsPasswordReset]
	           ,[IsSecretQuestionsSet]
	           ,[PasswordChangeDateTime]
	           ,[LoginFailedAttempts]
	           ,[LastModifiedDateTime]
	           ,[LastModifiedByUserName]
	           ,[IsDeleted]
	           ,[VersionNumber]
	           ,[UserType_FK]
	           ,[IsMandatoryContactInfoSet]
	           ,[IsLocked])
	     VALUES ( 
	     		'demo3',
	     		'demo3',
	     		@CONTACT_DEMO,
	     		0,
	     		1,
	     		getDate(),
	     		0,
	     		getDate(),
	     		'Systems',
	     		0,
	     		1,
	     		@TBS_USER_TYPE,
	     		1,
	     		0
	     	)
	     	END
	     	
SET @CONTACT_DEMO	= ( SELECT Contact_PK FROM Contacts WHERE FullName = 'demo user4' )
IF NOT EXISTS (SELECT * FROM AppUser WHERE Contact_FK = @CONTACT_DEMO)
BEGIN		     	
	INSERT INTO AppUser (
				[UserName]
	           ,[UserPassword]
	           ,[Contact_FK]
	           ,[IsPasswordReset]
	           ,[IsSecretQuestionsSet]
	           ,[PasswordChangeDateTime]
	           ,[LoginFailedAttempts]
	           ,[LastModifiedDateTime]
	           ,[LastModifiedByUserName]
	           ,[IsDeleted]
	           ,[VersionNumber]
	           ,[UserType_FK]
	           ,[IsMandatoryContactInfoSet]
	           ,[IsLocked])
	     VALUES ( 
	     		'demo4',
	     		'demo4',
	     		@CONTACT_DEMO,
	     		0,
	     		1,
	     		getDate(),
	     		0,
	     		getDate(),
	     		'Systems',
	     		0,
	     		1,
	     		@TBS_USER_TYPE,
	     		1,
	     		0
	     	)
END     	
GO

---------------------------------------------------------------------
-- User_Orders
---------------------------------------------------------------------
TRUNCATE TABLE User_Orders
IF NOT EXISTS (SELECT * FROM User_Orders)
BEGIN

	DECLARE 
		@USER_DEMO			decimal(18,0),
		@SECURITY_TBS_A		decimal(18,0),
		@ORDER_TYPE_SELL	decimal(18,0),
		@ORDER_TYPE_BUY	decimal(18,0),
		@STATUS_OPEN		decimal(18,0)
		
	
	SET @ORDER_TYPE_SELL	= ( SELECT Type_PK FROM Order_Types WHERE Code = 'SELL' )
	SET @ORDER_TYPE_BUY	= ( SELECT Type_PK FROM Order_Types WHERE Code = 'BUY' )
	SET @STATUS_OPEN		= ( SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'OPEN' )
	
	SET @USER_DEMO			= ( SELECT User_PK FROM AppUser WHERE UserName = 'demo' )
	SET @SECURITY_TBS_A		= ( SELECT Security_PK FROM Securities WHERE Name = 'A' )
	INSERT INTO User_Orders (User_FK, EntryDate, Security_FK, Type_FK, Quantity, Price, Status_FK) VALUES ( @USER_DEMO, '2012/12/04 12:05:00', @SECURITY_TBS_A, @ORDER_TYPE_SELL, 100.0, 5.0, @STATUS_OPEN )
	
	SET @USER_DEMO			= ( SELECT User_PK FROM AppUser WHERE UserName = 'demo2' )
	SET @SECURITY_TBS_A		= ( SELECT Security_PK FROM Securities WHERE Name = 'A' )
	INSERT INTO User_Orders (User_FK, EntryDate, Security_FK, Type_FK, Quantity, Price, Status_FK) VALUES ( @USER_DEMO, '2012/12/03 15:05:00', @SECURITY_TBS_A, @ORDER_TYPE_SELL, 300.0, 5.25, @STATUS_OPEN )
	
	SET @USER_DEMO			= ( SELECT User_PK FROM AppUser WHERE UserName = 'demo3' )
	SET @SECURITY_TBS_A		= ( SELECT Security_PK FROM Securities WHERE Name = 'A' )
	INSERT INTO User_Orders (User_FK, EntryDate, Security_FK, Type_FK, Quantity, Price, Status_FK) VALUES ( @USER_DEMO, '2012/12/06 07:15:00', @SECURITY_TBS_A, @ORDER_TYPE_SELL, 50.0, 6.0, @STATUS_OPEN )
	
	

	SET @USER_DEMO			= ( SELECT User_PK FROM AppUser WHERE UserName = 'demo4' )
	SET @SECURITY_TBS_A		= ( SELECT Security_PK FROM Securities WHERE Name = 'A' )
	INSERT INTO User_Orders (User_FK, EntryDate, Security_FK, Type_FK, Quantity, Price, Status_FK) VALUES ( @USER_DEMO, '2012/12/04 12:47:00', @SECURITY_TBS_A, @ORDER_TYPE_BUY, 500.0, 4.75, @STATUS_OPEN )

	SET @USER_DEMO			= ( SELECT User_PK FROM AppUser WHERE UserName = 'demo3' )
	SET @SECURITY_TBS_A		= ( SELECT Security_PK FROM Securities WHERE Name = 'A' )
	INSERT INTO User_Orders (User_FK, EntryDate, Security_FK, Type_FK, Quantity, Price, Status_FK) VALUES ( @USER_DEMO, '2012/12/01 19:25:00', @SECURITY_TBS_A, @ORDER_TYPE_BUY, 60.0, 4.2, @STATUS_OPEN )

	SET @USER_DEMO			= ( SELECT User_PK FROM AppUser WHERE UserName = 'demo' )
	SET @SECURITY_TBS_A		= ( SELECT Security_PK FROM Securities WHERE Name = 'A' )
	INSERT INTO User_Orders (User_FK, EntryDate, Security_FK, Type_FK, Quantity, Price, Status_FK) VALUES ( @USER_DEMO, '2012/12/01 12:05:00', @SECURITY_TBS_A, @ORDER_TYPE_BUY, 1000.0, 4.0, @STATUS_OPEN )

END
GO


