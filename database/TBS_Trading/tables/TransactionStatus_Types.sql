SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[TransactionStatus_Types](
	[TransactionStatus_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[TransactionStatus] [char](30) NOT NULL,
	[Description] [varchar](255) NOT NULL,
 CONSTRAINT [PK_TransactionStatus_Types] PRIMARY KEY CLUSTERED 
(
	[TransactionStatus_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
