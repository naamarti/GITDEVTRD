SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:	Val Catrini
-- Create date: Dec 19, 2012
--
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[p_Trading_InsertUserOrder]
	  @User_FK			decimal(18,0)
	, @Security_FK		decimal(18,0)
	, @Type_FK			decimal(18,0)
	, @Quantity			decimal(18,2)
	, @Price			decimal(18,4)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

		DECLARE 
			  @EntryDate	datetime
			, @Status_FK	decimal(18,0)
			, @Order_PK		decimal(18,0)
			
		
		SET @EntryDate		= getdate()
		SET @Status_FK		= ( SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'OPEN' )

		INSERT INTO User_Orders (
			  User_FK
			, EntryDate
			, Security_FK
			, Type_FK
			, Original_Quantity
			, Quantity
			, Price
			, Status_FK
		)
		VALUES (
			  @User_FK
			, @EntryDate
			, @Security_FK
			, @Type_FK
			, @Quantity
			, @Quantity
			, @Price
			, @Status_FK
		)

		SELECT @Order_PK = IDENT_CURRENT('User_Orders')
		
		SELECT dbo.f_GetUserOrderNumber(@Order_PK) AS OrderNumber

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
GO
