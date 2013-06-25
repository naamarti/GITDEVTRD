SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 
CREATE FUNCTION [dbo].[f_GetUserOrderNumberPK]
(
	@PaddedOrderNumber		char(7)
)
RETURNS decimal(18,0)

AS
BEGIN
	
	DECLARE	
		@OrderPK	decimal(18,0)
			
	SET @OrderPK = CONVERT( decimal(18,0), SUBSTRING( @PaddedOrderNumber, 2, (LEN(@PaddedOrderNumber) - 1)) ) 

	RETURN @OrderPK

END
GO
