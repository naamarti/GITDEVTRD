SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[p_Get_Roles]
	  @Action varchar(20)

AS
BEGIN
	SET NOCOUNT ON;

-- ***********************************************************************************************************

IF @Action = 'GET'
BEGIN

	SELECT Role_PK, RoleName, RoleDescription FROM AppRole

END

END
GO
