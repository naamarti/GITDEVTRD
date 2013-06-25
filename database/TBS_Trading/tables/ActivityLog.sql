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
