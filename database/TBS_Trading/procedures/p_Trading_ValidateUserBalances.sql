
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO

CREATE PROCEDURE [dbo].[p_Trading_ValidateUserBalances]
	@Security_FK	decimal(18,0) = 1
AS
BEGIN
SET NOCOUNT ON;

	BEGIN TRY

	DECLARE 
		  @SELL_TYPE_FK				decimal(18,0)
		, @OpenOrderStatus		    decimal(18,0)
		, @PartialOrderStatus       decimal(18,0)
		
		SET @SELL_TYPE_FK	          = ( SELECT Type_PK FROM Order_Types WHERE Code = 'SELL' )
		SET @OpenOrderStatus          = ( SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'OPEN' )
		SET @PartialOrderStatus       = ( SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'PARTIAL' )
		
		
	IF( OBJECT_ID('tempdb..#Validation') IS NOT NULL ) DROP TABLE #Validation

		CREATE TABLE #Validation (
			User_FK				decimal(18,0)	NOT NULL
			,UserName			varchar(30)		NOT NULL
			,QtySum				decimal(18,2)	NOT NULL
			,QtyAvail			decimal(18,2)	NOT NULL
		)

		INSERT INTO #Validation (
			User_FK, UserName, QtyAvail, QtySum )
		SELECT
			B.User_FK, U.UserName, MAX(B.Pending_Quantity), SUM(O.Quantity)
		FROM
			User_Orders O
			INNER JOIN User_Balances B ON B.User_FK = O.User_FK AND B.Security_FK = O.Security_FK
			INNER JOIN AppUser U ON B.User_FK = U.User_PK
		WHERE 
			O.Security_FK = @Security_FK AND O.Type_FK = @SELL_TYPE_FK 
		    AND O.Status_FK IN (@OpenOrderStatus, @PartialOrderStatus) 
		GROUP BY
			B.User_FK, U.UserName

		IF EXISTS (SELECT * FROM #Validation WHERE QtySum > QtyAvail)	
		BEGIN

			DECLARE @ErrorMessage varchar(5000)
			SET @ErrorMessage = 'Error! Quantity in transaction(s) for '
			SET @ErrorMessage = @ErrorMessage + (SELECT TOP 1 UserName FROM #Validation WHERE QtySum > QtyAvail)
			SET @ErrorMessage = @ErrorMessage + ' exceeds user balance. Please resolve before proceeding'

			RAISERROR(@ErrorMessage,16,1)
			RETURN

		END

		IF( OBJECT_ID('tempdb..#Validation') IS NOT NULL ) DROP TABLE #Validation

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END

GO