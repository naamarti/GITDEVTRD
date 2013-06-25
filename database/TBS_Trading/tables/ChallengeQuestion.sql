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