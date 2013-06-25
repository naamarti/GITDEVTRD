
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Created:  	13 Dec 2012 NM
 * Modified:	28 Mar 2013 NM - changed to only get "Active" orders
 *
 * Get Users's ACTIVE Order History
 *
 * =================================================================================================
 */
CREATE PROCEDURE [dbo].[p_Trading_GetActiveUserOrders]
 	  @User_FK           decimal(18,0)
	 ,@StartDate         datetime
	 ,@EndDate           datetime


AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;


	BEGIN TRY

		DECLARE
			@MAX_SUMMARY_RESULTS   	int,
			@ORDER_STATUS_CANCELED	decimal(18,0),
			@ORDER_STATUS_FILLED	decimal(18,0)
			--NOTE: we can make 3 separate sprocs, one to get all orders that are
			--		- ACTIVE (OPEN & PARTIAL)
			--      - CLOSED (FILLED & CANCELED)
			--      - ALL    (OPEN, PARTIAL, FILLED & CANCELED)
			
		SET @MAX_SUMMARY_RESULTS 	= 100
		SET @ORDER_STATUS_CANCELED	= ( SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'CANCELED' )
		SET @ORDER_STATUS_FILLED	= ( SELECT Type_PK FROM OrderStatus_Types WHERE Code = 'FILLED' )
		
		IF( OBJECT_ID('tempdb..#UserOrderSummary') IS NOT NULL ) DROP TABLE #UserOrderSummary

		CREATE TABLE #UserOrderSummary (
			OrderId			char(7)			NOT NULL, 
			EntryDate       datetime      	NOT NULL,
			Security        varchar(50) 	NOT NULL,
			Order_Type      varchar(50) 	NOT NULL,
			Original_Quantity decimal(18,2) NOT NULL,
			Quantity        decimal(18,2) 	NOT NULL,
			Price           decimal(18,4) 	NOT NULL,
			Status          varchar(50) 	NOT NULL
		)

		INSERT INTO #UserOrderSummary (
			OrderId,
			EntryDate,
			Security,
			Order_Type,
			Original_Quantity,
			Quantity,
			Price,
			Status
		)
		SELECT TOP 100
			TBS_Trading.dbo.f_GetUserOrderNumber(O.Order_PK),
			O.EntryDate,
			S.Name,
			OT.Code,
			O.Original_Quantity,
			O.Quantity,
			O.Price,
			OST.Code
		FROM User_Orders O WITH (NOLOCK)
		INNER JOIN Order_Types OT ON 
			OT.Type_PK = O.Type_FK
		INNER JOIN OrderStatus_Types OST ON 
			OST.Type_PK = O.Status_FK
		INNER JOIN Securities S ON  
			S.Security_PK = O.Security_FK
		WHERE O.User_FK = @User_FK
		  AND O.Status_FK NOT IN ( @ORDER_STATUS_CANCELED, @ORDER_STATUS_FILLED )
		  AND O.EntryDate >= @StartDate 
		  AND O.EntryDate <= @EndDate
		ORDER BY EntryDate DESC, Price DESC

	
					
		SELECT * 
		FROM #UserOrderSummary 
		ORDER BY EntryDate
		
	END TRY
	BEGIN CATCH
		DECLARE @ErrMsg nvarchar(4000), @ErrSeverity int
		SELECT @ErrMsg = ERROR_MESSAGE(), @ErrSeverity = ERROR_SEVERITY()
		RAISERROR(@ErrMsg, @ErrSeverity, 1)
	END CATCH

END
