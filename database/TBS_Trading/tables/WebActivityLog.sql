SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[WebActivityLog](
	[UserName] [varchar](50) NULL,
	[UserId] [varchar](50) NULL,
	[WebServerIP] [char](15) NULL,
	[WebDateTime] [datetime] NULL,
	[TBSSessionId] [varchar](50) NULL,
	[PageURL] [varchar](100) NULL,
	[Details] [varchar](100) NULL,
	[SQLDateStamp] [datetime] NULL CONSTRAINT [DF_WebActivityLog_SQLDateStamp]  DEFAULT (getdate()),
	[RemoteHost] [char](15) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
