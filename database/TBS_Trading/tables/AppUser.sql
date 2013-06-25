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
