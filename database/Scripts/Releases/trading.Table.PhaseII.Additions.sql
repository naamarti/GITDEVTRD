IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'Process_Date')
BEGIN
	SET ANSI_NULLS ON
	SET QUOTED_IDENTIFIER ON
	SET ANSI_PADDING ON
	CREATE TABLE [dbo].[Process_Date](
		[ProcessDate_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
		[ProcessDate] [datetime] NOT NULL,
		[ProcessStatus_FK] [decimal](18, 0) NOT NULL,
		[IsUndo] [bit] NOT NULL,
		[ModifiedDateTime] [datetime] NOT NULL,
		[User_FK] [decimal](18, 0) NOT NULL,
		[Details] [varchar](255) NOT NULL,
	 CONSTRAINT [PK_Process_Date] PRIMARY KEY NONCLUSTERED 
	(
		[ProcessDate_PK] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
	) ON [PRIMARY]
	SET ANSI_PADDING OFF
	CREATE UNIQUE CLUSTERED INDEX [IX_Process_Date] ON [dbo].[Process_Date] 
	(
		[ProcessDate] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
END
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'ProcessStatus_Types')
BEGIN
	SET ANSI_NULLS ON
	SET QUOTED_IDENTIFIER ON
	SET ANSI_PADDING ON
	CREATE TABLE [dbo].[ProcessStatus_Types](
		[ProcessStatus_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
		[ProcessStatus] [char](30) NOT NULL,
		[Description] [varchar](255) NOT NULL,
	 CONSTRAINT [PK_ProcessStatus_Types] PRIMARY KEY CLUSTERED 
	(
		[ProcessStatus_PK] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
	) ON [PRIMARY]
	SET ANSI_PADDING OFF
END
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'TransactionStatus_Types')
BEGIN
	SET ANSI_NULLS ON
	SET QUOTED_IDENTIFIER ON
	SET ANSI_PADDING ON
	CREATE TABLE [dbo].[TransactionStatus_Types](
		[TransactionStatus_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
		[TransactionStatus] [char](30) NOT NULL,
		[Description] [varchar](255) NOT NULL,
	 CONSTRAINT [PK_TransactionStatus_Types] PRIMARY KEY CLUSTERED 
	(
		[TransactionStatus_PK] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
	) ON [PRIMARY]
	SET ANSI_PADDING OFF
END
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'Trading_Settings')
BEGIN
	SET ANSI_NULLS ON
	SET QUOTED_IDENTIFIER ON
	SET ANSI_PADDING ON
	CREATE TABLE [dbo].[Trading_Settings](
		[OrderCutoffHour] [int] NOT NULL,
		[OrderCutoffMinute] [int] NOT NULL
	) ON [PRIMARY]
	SET ANSI_PADDING OFF
END
GO

IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'AppRole')
BEGIN
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
END
GO

drop table AppUser_AppRole_Relationship

IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'U' and name = 'AppUser_AppRole_Relationship')
BEGIN
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

END
GO
	ALTER TABLE [dbo].[AppUser_AppRole_Relationship]  WITH CHECK ADD CONSTRAINT [FK_AppUser_AppRole_Relationship_AppRole] FOREIGN KEY([Role_FK])
	REFERENCES [dbo].[AppRole] ([Role_PK])
	GO

	ALTER TABLE [dbo].[AppUser_AppRole_Relationship] CHECK CONSTRAINT [FK_AppUser_AppRole_Relationship_AppRole]
	GO

	ALTER TABLE [dbo].[AppUser_AppRole_Relationship]  WITH CHECK ADD  CONSTRAINT [FK_AppUser_AppRole_Relationship_AppUser] FOREIGN KEY([User_FK])
	REFERENCES [dbo].[AppUser] ([User_PK])
	GO

	ALTER TABLE [dbo].[AppUser_AppRole_Relationship] CHECK CONSTRAINT [FK_AppUser_AppRole_Relationship_AppUser]
	GO


