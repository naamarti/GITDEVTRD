SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 
CREATE FUNCTION [dbo].[f_GetUserTransactionNumber]
(
	@Transaction_FK		decimal(18,0)
)
RETURNS varchar(300)

AS
BEGIN
	
	DECLARE 
		@FirstChar			char(1),
		@PaddingChar		char(1),
		@TxNumberBase		varchar(6),
		@FullTxNumber		char(7),
		@TxNumberLength		int
		
	SET @FirstChar 			= 'T'  		--'O' for order, 'T' for transaction 
	SET @PaddingChar 		= '0'
	SET @TxNumberLength		= 7
	SET @TxNumberBase		= CONVERT(varchar, @Transaction_FK)
		
	--------------------------------------------------------
	---- Order # padded to 6 chars, with leading zeros
	--------------------------------------------------------
	SET @FullTxNumber = @FirstChar + replicate(@PaddingChar, @TxNumberLength - (LEN(@TxNumberBase) + 1)) + @TxNumberBase

	RETURN @FullTxNumber

END
GO
