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
