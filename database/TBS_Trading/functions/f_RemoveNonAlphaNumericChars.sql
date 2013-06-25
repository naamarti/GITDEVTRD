SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[f_RemoveNonAlphaNumericChars]
(
	@Data varchar(1000)
)
RETURNS varchar(1000)
AS

BEGIN

	WHILE PatIndex('%[^A-Za-z0-9]%', @Data) > 0
		SET @Data = Stuff(@Data, PatIndex('%[^A-Za-z0-9]%', @Data), 1, '')

 RETURN @Data

END
GO
