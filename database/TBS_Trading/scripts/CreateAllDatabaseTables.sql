 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppNavigationGroup](
	[NavigationGroup_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[GroupName] [varchar](30) NOT NULL,
	[GroupOrder] [int] NOT NULL,
 CONSTRAINT [PK_AppNavigationGroup] PRIMARY KEY NONCLUSTERED 
(
	[NavigationGroup_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_AppNavigationGroup_Name] UNIQUE NONCLUSTERED 
(
	[GroupName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MailingCountries](
	[Country_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Country] [varchar](50) NOT NULL,
	[PostalCode] [char](2) NOT NULL,
 CONSTRAINT [PK_MailingCountries] PRIMARY KEY CLUSTERED 
(
	[Country_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MailingStates](
	[State_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[State] [varchar](50) NOT NULL,
	[PostalCode] [char](2) NOT NULL,
 CONSTRAINT [PK_MailingStates] PRIMARY KEY CLUSTERED 
(
	[State_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppNavigation](
	[Navigation_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[NavigationGroup_FK] [decimal](18, 0) NOT NULL,
	[NavigationName] [varchar](30) NOT NULL,
	[NavigationOrder] [int] NOT NULL,
	[NavigationURL] [varchar](250) NOT NULL,
 CONSTRAINT [PK_AppNavigation] PRIMARY KEY NONCLUSTERED 
(
	[Navigation_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_AppNavigation_Name] UNIQUE NONCLUSTERED 
(
	[NavigationGroup_FK] ASC,
	[NavigationName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[AppNavigation]  WITH CHECK ADD  CONSTRAINT [FK_AppNavigation_AppNavigationGroup] FOREIGN KEY([NavigationGroup_FK])
REFERENCES [dbo].[AppNavigationGroup] ([NavigationGroup_PK])
GO
ALTER TABLE [dbo].[AppNavigation] CHECK CONSTRAINT [FK_AppNavigation_AppNavigationGroup]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppRole](
	[Role_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[RoleName] [varchar](50) NOT NULL,
	[RoleDescription] [varchar](255) NOT NULL,
 CONSTRAINT [PK_AppRole] PRIMARY KEY CLUSTERED 
(
	[Role_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_AppRole] UNIQUE NONCLUSTERED 
(
	[RoleName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppUser](
	[User_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[UserName] [varchar](30) NOT NULL,
	[UserPassword] [char](40) NOT NULL,
	[Contact_FK] [decimal](18, 0) NOT NULL,
	[IsPasswordReset] [bit] NOT NULL,
	[IsSecretQuestionsSet] [bit] NOT NULL,
	[PasswordChangeDateTime] [datetime] NOT NULL,
	[LoginFailedAttempts] [smallint] NOT NULL,
	[LastModifiedDateTime] [datetime] NOT NULL,
	[LastModifiedByUserName] [varchar](30) NOT NULL,
	[IsDeleted] [bit] NOT NULL,
	[VersionNumber] [smallint] NOT NULL,
	[UserType_FK] [decimal](18, 0) NOT NULL,
	[IsMandatoryContactInfoSet] [bit] NOT NULL CONSTRAINT [DF_AppUser_IsMandatoryContactInfoSet]  DEFAULT ((0)),
	[IsLocked] [bit] NOT NULL DEFAULT ((0)),
 CONSTRAINT [PK_AppUser] PRIMARY KEY NONCLUSTERED 
(
	[User_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_AppUser_UserName] UNIQUE NONCLUSTERED 
(
	[UserName] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ChallengeQuestion](
	[ChallengeQuestion_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[ChallengeQuestion] [varchar](255) NOT NULL,
 CONSTRAINT [PK_ChallengeQuestion] PRIMARY KEY CLUSTERED 
(
	[ChallengeQuestion_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_ChallengeQuestion] UNIQUE NONCLUSTERED 
(
	[ChallengeQuestion] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ContactInstitutionType](
	[ContactType_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[ContactInstitutionType] [char](50) NOT NULL,
 CONSTRAINT [PK_ContactInstitutionType] PRIMARY KEY CLUSTERED 
(
	[ContactType_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ErrorCodes](
	[ErrorCode_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[CodeNumber] [decimal](18, 0) NOT NULL,
	[Name] [varchar](50) NOT NULL,
	[Description] [varchar](255) NOT NULL,
 CONSTRAINT [PK_ErrorCodes] PRIMARY KEY CLUSTERED 
(
	[ErrorCode_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_ErrorCodes_CodeNumber] UNIQUE NONCLUSTERED 
(
	[CodeNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MailingAddresses](
	[MailingAddress_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Line1] [varchar](50) NULL,
	[Line2] [varchar](50) NULL,
	[Line3] [varchar](50) NULL,
	[Line4] [varchar](50) NULL,
	[City] [varchar](50) NULL,
	[State_FK] [decimal](18, 0) NOT NULL,
	[ZipCode] [char](5) NULL,
	[Country_FK] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_MailingAddresses] PRIMARY KEY CLUSTERED 
(
	[MailingAddress_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[MailingAddresses]  WITH CHECK ADD  CONSTRAINT [FK_MailingAddresses_MailingCountries] FOREIGN KEY([Country_FK])
REFERENCES [dbo].[MailingCountries] ([Country_PK])
GO
ALTER TABLE [dbo].[MailingAddresses] CHECK CONSTRAINT [FK_MailingAddresses_MailingCountries]
GO
ALTER TABLE [dbo].[MailingAddresses]  WITH CHECK ADD  CONSTRAINT [FK_MailingAddresses_MailingStates] FOREIGN KEY([State_FK])
REFERENCES [dbo].[MailingStates] ([State_PK])
GO
ALTER TABLE [dbo].[MailingAddresses] CHECK CONSTRAINT [FK_MailingAddresses_MailingStates]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ActivityLog](
	[User_FK] [decimal](18, 0) NOT NULL,
	[Activity] [varchar](255) NOT NULL,
	[EntryDateTime] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[ActivityLog]  WITH CHECK ADD  CONSTRAINT [FK_AppUser_ActivityLog] FOREIGN KEY([User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[ActivityLog] CHECK CONSTRAINT [FK_AppUser_ActivityLog]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppConfiguration](
	[AppConfiguration_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[DefaultUserPassword] [char](40) NOT NULL,
	[MinUsernameLength] [int] NOT NULL,
	[MinChallengeQuestions] [int] NOT NULL,
	[RandomChallengeQuestions] [int] NOT NULL,
	[SessionTimeoutMinutes] [int] NOT NULL,
	[WebLogging] [bit] NOT NULL,
	[EnvironmentName] [char](50) NOT NULL,
	[PasswordMinLength] [int] NOT NULL,
	[PasswordValidPeriodInDays] [int] NOT NULL,
	[PasswordNumCaps] [int] NOT NULL,
	[PasswordNumNumeric] [int] NOT NULL,
	[PasswordNumHistoricRestrict] [int] NOT NULL,
	[IsMandatoryContactInfoEnabled] [bit] NOT NULL CONSTRAINT [DF_AppConfiguration_IsMandatoryContactInfoEnabled]  DEFAULT ((0)),
	[NumLoginAttemptsBeforeLockout] [int] NOT NULL DEFAULT ((3)),
	[VersionNumber] [char](30) NOT NULL DEFAULT ('01.00.00'),
	[ReconLockOutDays] [int] NOT NULL,
	[JavaVersionNumber] [char](30) NULL,
	[DotNetVersionNumber] [char](30) NULL,
	[DBVersionNumber] [char](30) NULL,
	[IsTBSInternalTestMode] [bit] NOT NULL,
 CONSTRAINT [PK_AppConfiguration] PRIMARY KEY CLUSTERED 
(
	[AppConfiguration_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppNavigation_AppRole_Relationship](
	[Relationship_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Navigation_FK] [decimal](18, 0) NOT NULL,
	[Role_FK] [decimal](18, 0) NOT NULL,
	[IsFullAccess] [bit] NOT NULL,
 CONSTRAINT [PK_AppNavigation_AppRole_Relationship] PRIMARY KEY CLUSTERED 
(
	[Relationship_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_AppNavigation_AppRole_Relationship] UNIQUE NONCLUSTERED 
(
	[Navigation_FK] ASC,
	[Role_FK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[AppNavigation_AppRole_Relationship]  WITH CHECK ADD  CONSTRAINT [FK_AppNavigation_AppRole_Relationship_AppNavigation] FOREIGN KEY([Navigation_FK])
REFERENCES [dbo].[AppNavigation] ([Navigation_PK])
GO
ALTER TABLE [dbo].[AppNavigation_AppRole_Relationship] CHECK CONSTRAINT [FK_AppNavigation_AppRole_Relationship_AppNavigation]
GO
ALTER TABLE [dbo].[AppNavigation_AppRole_Relationship]  WITH CHECK ADD  CONSTRAINT [FK_AppNavigation_AppRole_Relationship_AppRole] FOREIGN KEY([Role_FK])
REFERENCES [dbo].[AppRole] ([Role_PK])
GO
ALTER TABLE [dbo].[AppNavigation_AppRole_Relationship] CHECK CONSTRAINT [FK_AppNavigation_AppRole_Relationship_AppRole]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppSession](
	[Session_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[User_FK] [decimal](18, 0) NOT NULL,
	[SessionKey] [char](40) NOT NULL,
	[LoginStatus] [int] NOT NULL,
	[LoginDateTime] [datetime] NOT NULL,
	[LastAccessDateTime] [datetime] NOT NULL,
	[Navigation_FK] [decimal](18, 0) NOT NULL,
	[IsDeleted] [bit] NOT NULL,
	[WorkingDate] [datetime] NULL,
 CONSTRAINT [PK_AppSession] PRIMARY KEY NONCLUSTERED 
(
	[Session_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_AppSession] UNIQUE NONCLUSTERED 
(
	[User_FK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[AppSession]  WITH CHECK ADD  CONSTRAINT [FK_AppSession_AppUser] FOREIGN KEY([User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[AppSession] CHECK CONSTRAINT [FK_AppSession_AppUser]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppUser_AppRole_Relationship](
	[Relationship_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[User_FK] [decimal](18, 0) NOT NULL,
	[Role_FK] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_AppUser_AppRole_Relationship] PRIMARY KEY CLUSTERED 
(
	[Relationship_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_AppUser_AppRole_Relationship] UNIQUE NONCLUSTERED 
(
	[User_FK] ASC,
	[Role_FK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[AppUser_AppRole_Relationship]  WITH CHECK ADD  CONSTRAINT [FK_AppUser_AppRole_Relationship_AppRole] FOREIGN KEY([Role_FK])
REFERENCES [dbo].[AppRole] ([Role_PK])
GO
ALTER TABLE [dbo].[AppUser_AppRole_Relationship] CHECK CONSTRAINT [FK_AppUser_AppRole_Relationship_AppRole]
GO
ALTER TABLE [dbo].[AppUser_AppRole_Relationship]  WITH CHECK ADD  CONSTRAINT [FK_AppUser_AppRole_Relationship_AppUser] FOREIGN KEY([User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[AppUser_AppRole_Relationship] CHECK CONSTRAINT [FK_AppUser_AppRole_Relationship_AppUser]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppUser_ChallengeQuestion_Relationship](
	[Relationship_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[User_FK] [decimal](18, 0) NOT NULL,
	[ChallengeQuestion_FK] [decimal](18, 0) NOT NULL,
	[Response] [varchar](100) NOT NULL,
 CONSTRAINT [PK_AppUser_ChallengeQuestion_Relationship] PRIMARY KEY CLUSTERED 
(
	[Relationship_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_AppUser_ChallengeQuestion_Relationship] UNIQUE NONCLUSTERED 
(
	[User_FK] ASC,
	[ChallengeQuestion_FK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[AppUser_ChallengeQuestion_Relationship]  WITH CHECK ADD  CONSTRAINT [FK_AppUser_ChallengeQuestion_Relationship_AppUser] FOREIGN KEY([User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[AppUser_ChallengeQuestion_Relationship] CHECK CONSTRAINT [FK_AppUser_ChallengeQuestion_Relationship_AppUser]
GO
ALTER TABLE [dbo].[AppUser_ChallengeQuestion_Relationship]  WITH CHECK ADD  CONSTRAINT [FK_AppUser_ChallengeQuestion_Relationship_ChallengeQuestion] FOREIGN KEY([ChallengeQuestion_FK])
REFERENCES [dbo].[ChallengeQuestion] ([ChallengeQuestion_PK])
GO
ALTER TABLE [dbo].[AppUser_ChallengeQuestion_Relationship] CHECK CONSTRAINT [FK_AppUser_ChallengeQuestion_Relationship_ChallengeQuestion]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppUser_History](
	[AppUser_History_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[AppUser_FK] [decimal](18, 0) NOT NULL,
	[UserPassword] [char](40) NOT NULL,
	[DateAdded] [datetime] NOT NULL,
 CONSTRAINT [PK_AppUser_History] PRIMARY KEY NONCLUSTERED 
(
	[AppUser_History_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[AppUserType](
	[UserType_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[UserType] [char](50) NOT NULL,
	[Description] [varchar](255) NOT NULL,
 CONSTRAINT [PK_AppUserType] PRIMARY KEY CLUSTERED 
(
	[UserType_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_AppUserType] UNIQUE NONCLUSTERED 
(
	[UserType] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Contacts](
	[Contact_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[ContactType] [char](10) NOT NULL,
	[FullName] [varchar](50) NOT NULL,
	[NameTitle] [char](5) NULL,
	[FirstName] [varchar](50) NULL,
	[MiddleName] [varchar](50) NULL,
	[LastName] [varchar](50) NULL,
	[NameSuffix] [char](30) NULL,
	[CompanyName] [varchar](50) NULL,
	[DepartmentName] [varchar](50) NULL,
	[JobTitle] [varchar](50) NULL,
	[BusinessPhone] [varchar](50) NULL,
	[BusinessFax] [varchar](50) NULL,
	[MobilePhone] [varchar](50) NULL,
	[EmailAddress] [varchar](100) NOT NULL,
	[MailingAddress_FK] [decimal](18, 0) NULL,
	[Comments] [char](255) NULL,
	[LastModifiedDateTime] [datetime] NOT NULL,
	[LastModifiedByUserName] [varchar](30) NOT NULL,
	[IsDeleted] [bit] NOT NULL,
	[VersionNumber] [smallint] NOT NULL,
	[ContactInstitutionType_FK] [decimal](18, 0) NULL,
 CONSTRAINT [PK_Contacts] PRIMARY KEY CLUSTERED 
(
	[Contact_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[Contacts]  WITH CHECK ADD  CONSTRAINT [FK_ContactInstitutionType_Contacts] FOREIGN KEY([ContactInstitutionType_FK])
REFERENCES [dbo].[ContactInstitutionType] ([ContactType_PK])
GO
ALTER TABLE [dbo].[Contacts] CHECK CONSTRAINT [FK_ContactInstitutionType_Contacts]
GO
ALTER TABLE [dbo].[Contacts]  WITH CHECK ADD  CONSTRAINT [FK_Contacts_MailingAddresses] FOREIGN KEY([MailingAddress_FK])
REFERENCES [dbo].[MailingAddresses] ([MailingAddress_PK])
GO
ALTER TABLE [dbo].[Contacts] CHECK CONSTRAINT [FK_Contacts_MailingAddresses]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EnvelopeTypes](
	[EnvelopeType_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Type] [varchar](50) NULL,
 CONSTRAINT [PK_EnvelopeTypes] PRIMARY KEY CLUSTERED 
(
	[EnvelopeType_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Order_Types](
	[Type_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Code] [varchar](50) NOT NULL,
	[Description] [varchar](500) NOT NULL,
 CONSTRAINT [PK_Order_Types] PRIMARY KEY CLUSTERED 
(
	[Type_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[OrderStatus_Types](
	[Type_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Code] [varchar](50) NOT NULL,
	[Description] [varchar](500) NOT NULL,
 CONSTRAINT [PK_OrderStatus_Types] PRIMARY KEY CLUSTERED 
(
	[Type_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ProcessingLog](
	[ProcessingLog_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[ItemTimeStamp] [datetime] NOT NULL,
	[UserName] [varchar](50) NOT NULL,
	[Category] [varchar](50) NOT NULL,
	[Description] [varchar](1000) NOT NULL,
	[ErrorCode_FK] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_ProcessingLog] PRIMARY KEY CLUSTERED 
(
	[ProcessingLog_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[ProcessingLog]  WITH CHECK ADD  CONSTRAINT [FK_ProcessingLog_ErrorCodes] FOREIGN KEY([ErrorCode_FK])
REFERENCES [dbo].[ErrorCodes] ([ErrorCode_PK])
GO
ALTER TABLE [dbo].[ProcessingLog] CHECK CONSTRAINT [FK_ProcessingLog_ErrorCodes]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Securities](
	[Security_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Name] [char](50) NOT NULL,
	[Description] [char](50) NOT NULL,
	[User_FK] [decimal](18, 0) NULL,
	[LastUpdated] [datetime] NULL,
 CONSTRAINT [PK_Security] PRIMARY KEY CLUSTERED 
(
	[Security_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Trading_UserSettings](
	[User_FK] [decimal](18, 0) NOT NULL,
	[IsDisclaimerApproved] [bit] NOT NULL,
 CONSTRAINT [PK_Trading_UserSettings] PRIMARY KEY CLUSTERED 
(
	[User_FK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[Trading_UserSettings]  WITH CHECK ADD  CONSTRAINT [FK_Trading_UserSettings_Users] FOREIGN KEY([User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[Trading_UserSettings] CHECK CONSTRAINT [FK_Trading_UserSettings_Users]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Transaction_Types](
	[TransactionType_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[TransactionType] [char](30) NOT NULL,
	[TransactionAbbrev] [char](3) NOT NULL,
 CONSTRAINT [PK_Transaction_Types] PRIMARY KEY CLUSTERED 
(
	[TransactionType_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TransactionExceptionType](
	[ExceptionType_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[ExceptionType] [char](100) NOT NULL,
	[Description] [varchar](255) NOT NULL,
 CONSTRAINT [PK_TransactionExceptionType] PRIMARY KEY CLUSTERED 
(
	[ExceptionType_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[User_Balances](
	[Balance_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[User_FK] [decimal](18, 0) NOT NULL,
	[Security_FK] [decimal](18, 0) NOT NULL,
	[BalanceDate] [datetime] NOT NULL,
	[Quantity] [decimal](18, 2) NULL,
	[Balance] [decimal](18, 4) NOT NULL,
	[LastTransactionDate] [datetime] NOT NULL,
 CONSTRAINT [PK_User_Balances] PRIMARY KEY CLUSTERED 
(
	[Balance_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [UK_User_Balances] UNIQUE NONCLUSTERED 
(
	[User_FK] ASC,
	[Security_FK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
ALTER TABLE [dbo].[User_Balances]  WITH CHECK ADD  CONSTRAINT [FK_User_Balances_Users] FOREIGN KEY([User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[User_Balances] CHECK CONSTRAINT [FK_User_Balances_Users]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[User_Orders](
	[Order_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[User_FK] [decimal](18, 0) NOT NULL,
	[EntryDate] [datetime] NOT NULL,
	[Security_FK] [decimal](18, 0) NOT NULL,
	[Type_FK] [decimal](18, 0) NOT NULL,
	[Quantity] [decimal](18, 2) NULL,
	[Price] [decimal](18, 4) NOT NULL,
	[Status_FK] [decimal](18, 0) NOT NULL,
 CONSTRAINT [PK_User_Orders] PRIMARY KEY CLUSTERED 
(
	[Order_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Order_Date] ON [dbo].[User_Orders]
(
	[User_FK] ASC,
	[EntryDate] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[User_Orders]  WITH CHECK ADD  CONSTRAINT [FK_User_Orders_Users] FOREIGN KEY([User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[User_Orders] CHECK CONSTRAINT [FK_User_Orders_Users]
GO
 
go 
 
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[User_Transactions](
	[Transaction_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Quantity] [decimal](18, 2) NULL,
	[Price] [decimal](18, 4) NOT NULL,
	[Buy_User_FK] [decimal](18, 0) NOT NULL,
	[Buy_Order_FK] [decimal](18, 0) NOT NULL,
	[Sell_User_FK] [decimal](18, 0) NOT NULL,
	[Sell_Order_FK] [decimal](18, 0) NOT NULL,
	[EffectiveDate] [datetime] NOT NULL,
 CONSTRAINT [PK_User_Transaction] PRIMARY KEY CLUSTERED 
(
	[Transaction_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Buy_AccountDate] ON [dbo].[User_Transactions]
(
	[Buy_User_FK] ASC,
	[EffectiveDate] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Sell_AccountDate] ON [dbo].[User_Transactions]
(
	[Sell_User_FK] ASC,
	[EffectiveDate] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[User_Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Buy_User_Transactions_Users] FOREIGN KEY([Buy_User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[User_Transactions] CHECK CONSTRAINT [FK_Buy_User_Transactions_Users]
GO
ALTER TABLE [dbo].[User_Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Sell_User_Transactions_Users] FOREIGN KEY([Sell_User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[User_Transactions] CHECK CONSTRAINT [FK_Sell_User_Transactions_Users]
GO
 
go 
 
