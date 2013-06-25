SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[User_Transactions](
	[Transaction_PK] [decimal](18, 0) IDENTITY(1,1) NOT NULL,
	[Quantity] [decimal](18, 2) NULL,
	[Price] [decimal](18, 4) NOT NULL,
	[Buy_User_FK] [decimal](18, 0) NOT NULL,
	[Buy_Order_FK] [decimal](18, 0) NOT NULL,
	[Sell_User_FK] [decimal](18, 0) NOT NULL,
	[Sell_Order_FK] [decimal](18, 0) NOT NULL,
	[EffectiveDate] [datetime] NOT NULL,
	[TransactionStatus_FK] [decimal](18, 0) NOT NULL,
	[ProcessDate_FK] [decimal](18, 0) NOT NULL,	
	[Security_FK] [decimal](18, 0) NOT NULL,	
 CONSTRAINT [PK_User_Transaction] PRIMARY KEY CLUSTERED 
(
	[Transaction_PK] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
CREATE NONCLUSTERED INDEX [IX_Buy_AccountDate] ON [dbo].[User_Transactions]
(
	[Buy_User_FK] ASC,
	[EffectiveDate] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [IX_Sell_AccountDate] ON [dbo].[User_Transactions]
(
	[Sell_User_FK] ASC,
	[EffectiveDate] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[User_Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Buy_User_Transactions_Users] FOREIGN KEY([Buy_User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[User_Transactions] CHECK CONSTRAINT [FK_Buy_User_Transactions_Users]
GO
ALTER TABLE [dbo].[User_Transactions]  WITH CHECK ADD  CONSTRAINT [FK_Sell_User_Transactions_Users] FOREIGN KEY([Sell_User_FK])
REFERENCES [dbo].[AppUser] ([User_PK])
GO
ALTER TABLE [dbo].[User_Transactions] CHECK CONSTRAINT [FK_Sell_User_Transactions_Users]
GO
ALTER TABLE [dbo].[User_Transactions]  WITH CHECK ADD  CONSTRAINT [FK_ProcessDateFK_ProcessDate] FOREIGN KEY([ProcessDate_FK])
REFERENCES [dbo].[Process_Date] ([ProcessDate_PK])
GO
ALTER TABLE [dbo].[User_Transactions] CHECK CONSTRAINT [FK_ProcessDateFK_ProcessDate]
GO
