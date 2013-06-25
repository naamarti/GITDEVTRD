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
