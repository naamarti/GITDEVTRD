SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Created:  	01 Mar 2013 VC
 * Modified:	
 *
 * Get Users's Order
 *
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_GetUserOrder]
	  @User_FK			decimal(18,0)
	, @OrderId			char(7)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		DECLARE
			@Order_PK	decimal(18,0)
			
		SET @Order_PK = dbo.f_GetUserOrderNumberPK(@OrderId)

		SELECT
			  dbo.f_GetUserOrderNumber(O.Order_PK) AS OrderId
			, O.EntryDate
			, S.Name AS Security
			, OT.Code AS Order_Type
			, O.Original_Quantity
			, O.Quantity
			, O.Price
			, (O.Quantity * O.Price) AS EstimatedValue
			, OST.Code AS Status
		FROM User_Orders O WITH (NOLOCK)
		INNER JOIN Order_Types OT WITH (NOLOCK) ON OT.Type_PK = O.Type_FK
		INNER JOIN OrderStatus_Types OST WITH (NOLOCK) ON OST.Type_PK = O.Status_FK
		INNER JOIN Securities S WITH (NOLOCK) ON S.Security_PK = O.Security_FK
		WHERE O.User_FK = @User_FK
		AND O.Order_PK = @Order_PK

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
