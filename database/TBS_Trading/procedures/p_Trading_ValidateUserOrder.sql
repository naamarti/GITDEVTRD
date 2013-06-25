
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:	Val Catrini
-- Create date: Dec 20, 2012
-- Modified: AW, Mar 21, 2013, GEM# 2283	
--
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[p_Trading_ValidateUserOrder]
	  @User_FK			decimal(18,0)
	, @Security_FK		decimal(18,0)
	, @Type_FK			decimal(18,0)
	, @Quantity			decimal(18,4)
	, @Price			decimal(18,4)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY

	DECLARE 
		  @UserQuantity				decimal(18,4)
		, @UserPendingQuantity		decimal(18,4)
		, @UserPendingSellQuantity	decimal(18,4)
		, @SELL_TYPE_FK				decimal(18,0)
		, @Message					varchar(500)
		, @OpenOrderStatus		    decimal(18,0)
		, @PartialOrderStatus       decimal(18,0)
		


		SET @SELL_TYPE_FK	          = ( SELECT Type_PK FROM Order_Types WHERE Code = 'SELL' )
		SET @OpenOrderStatus          = (SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'OPEN')
		SET @PartialOrderStatus       = (SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'PARTIAL')
		SET @UserQuantity	          = ISNULL(( SELECT Quantity FROM User_Balances WHERE User_FK = @User_FK AND Security_FK = @Security_FK ),0)
		SET @UserPendingQuantity	  = ISNULL(( SELECT Pending_Quantity FROM User_Balances WHERE User_FK = @User_FK AND Security_FK = @Security_FK ),0)
		SET @UserPendingQuantity = @UserPendingQuantity - ISNULL(( SELECT SUM(quantity) FROM User_Orders WHERE User_FK = @User_FK 
		                                                           AND Security_FK = @Security_FK AND Type_FK = @SELL_TYPE_FK 
		                                                           AND Status_FK IN (@OpenOrderStatus, @PartialOrderStatus) 
		                                                           ),0)


		IF( OBJECT_ID('tempdb..#Validations') IS NOT NULL ) DROP TABLE #Validations
		
		CREATE TABLE #Validations (
			ColumnName						varchar(100)		NOT NULL,
			IsValid							bit					NOT NULL,
			ValidationMessage				varchar(500)		NOT NULL,
			PRIMARY KEY CLUSTERED ( ColumnName )
		)

		IF @Quantity <= 0
		BEGIN
			SET @Message = 'Please enter a Quantity greater than 0.'
			INSERT INTO #Validations SELECT 'quantity', 'FALSE', @Message
		END

		IF @Price <= 0
		BEGIN
			SET @Message = 'Please enter a Price greater than 0.'
			INSERT INTO #Validations SELECT 'price', 'FALSE', @Message
		END

		IF @Type_FK = @SELL_TYPE_FK
		BEGIN
			IF @UserQuantity <= 0
			BEGIN
				SET @Message = 'You cannot place a SELL order since you do not have an open position in this security.'
				INSERT INTO #Validations SELECT 'securityId', 'FALSE', @Message
				INSERT INTO #Validations SELECT 'orderTypeId', 'FALSE', ''
			END
			ELSE 
				IF @UserPendingQuantity < @UserQuantity
				BEGIN
					IF @UserPendingQuantity < @Quantity
					BEGIN
						SET @Message = 'Can only sell up to [ ' + REPLACE(CONVERT(varchar,CONVERT(money, @UserPendingQuantity),1),'.00','') + ' ] shares, which is your open pending position in this security.'
						INSERT INTO #Validations SELECT 'quantity', 'FALSE', @Message
					END
				END
				ELSE
				BEGIN
					IF @UserQuantity < @Quantity
					BEGIN
						SET @Message = 'Can only sell up to [ ' + REPLACE(CONVERT(varchar,CONVERT(money, @UserQuantity),1),'.00','') + ' ] shares, which is your open position in this security.'
						INSERT INTO #Validations SELECT 'quantity', 'FALSE', @Message
					END
				END
		END

		SELECT * FROM #Validations
		ORDER BY ColumnName

	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
