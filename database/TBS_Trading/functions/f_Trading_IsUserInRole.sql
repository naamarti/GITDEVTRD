SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Nate Martin
-- Create date: Mar 25, 2013
-- Modified: 	
-- Description:	
-- =============================================
CREATE FUNCTION [dbo].[f_Trading_IsUserInRole] 
(
	  @RoleName		varchar(20)
	 ,@User_FK		decimal(18,0)
)
RETURNS bit
AS
BEGIN
	DECLARE  @IsRole	bit
			,@Role_FK	decimal(18,0)
			
 --,@RoleName		varchar(20)
 --,@User_FK		decimal(18,0)	 
 --SET @RoleName = 'finance'
 --SET @User_FK = 119	
		
	SELECT	@Role_FK	= (SELECT Role_PK FROM AppRole WHERE RoleName = @RoleName)
	SET		@IsRole		= 0
	
	IF  EXISTS (SELECT * FROM AppUser_AppRole_Relationship WHERE User_FK = @User_FK and Role_FK = @Role_FK) 
		SET @IsRole = 1
		
	RETURN @IsRole

END

GO
