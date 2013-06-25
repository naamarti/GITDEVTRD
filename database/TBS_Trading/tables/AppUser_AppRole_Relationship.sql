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
