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
	[Pending_Quantity] [decimal](18, 2) NOT NULL,
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
