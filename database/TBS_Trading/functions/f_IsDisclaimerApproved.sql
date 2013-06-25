SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Created:  	18 Jan 2013 DJM
 * Modified:	
 *
 * Has the site's disclaimer agreement been approved by the given user
 *
 * =================================================================================================
 */
CREATE FUNCTION [dbo].[f_IsDisclaimerApproved](
 	  @User_FK    	decimal(18,0)
)
RETURNS bit
AS
BEGIN
	DECLARE @IsDisclaimerApproved bit

	SET @IsDisclaimerApproved	= ISNULL(( SELECT IsDisclaimerApproved	FROM Trading_UserSettings WHERE User_FK = @User_FK ), 0 )

	RETURN @IsDisclaimerApproved
END
GO
