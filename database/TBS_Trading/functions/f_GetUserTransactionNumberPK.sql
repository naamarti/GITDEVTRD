SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 
CREATE FUNCTION [dbo].[f_GetUserTransactionNumberPK]
(
	@PaddedTxNumber		char(7)
)
RETURNS decimal(18,0)

AS
BEGIN
	
	DECLARE	
		@TransactionPK	decimal(18,0)
			
	SET @TransactionPK = CONVERT( decimal(18,0), SUBSTRING( @PaddedTxNumber, 2, (LEN(@PaddedTxNumber) - 1)) ) 

	RETURN @TransactionPK

END
GO
