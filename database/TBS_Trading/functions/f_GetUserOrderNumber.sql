SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 
CREATE FUNCTION [dbo].[f_GetUserOrderNumber]
(
	@Order_FK		decimal(18,0)
)
RETURNS varchar(300)

AS
BEGIN
	
	DECLARE 
		@FirstChar			char(1),
		@PaddingChar		char(1),
		@OrderNumberBase	varchar(6),
		@FullOrderNumber	char(7),
		@OrderNumberLength	int
		
	SET @FirstChar 			= 'R'  		--'O' for order, 'T' for transaction 
	SET @PaddingChar 		= '0'
	SET @OrderNumberLength	= 7
	SET @OrderNumberBase	= CONVERT(varchar, @Order_FK)
		
	--------------------------------------------------------
	---- Order # padded to 6 chars, with leading zeros
	--------------------------------------------------------
	SET @FullOrderNumber = @FirstChar + replicate(@PaddingChar, @OrderNumberLength - (LEN(@OrderNumberBase) + 1)) + @OrderNumberBase

	RETURN @FullOrderNumber

END
GO
