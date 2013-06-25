SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/* =================================================================================================
 * Modified:	
 *
 *
 * =================================================================================================
 */
CREATE FUNCTION [dbo].[f_IsOrdersLocked](
)
RETURNS bit
AS
BEGIN
	DECLARE 
		  @IsOrdersLocked			bit
		, @CurrentMonth				int
		, @ProcessDate				datetime
		, @CutoffTime				varchar(10)
		, @ProcessDateTime			datetime
		, @ProcessStatus_FK			datetime
		, @PROCESS_STATUS_SUCCESS	decimal(18,0)
	
	SET @IsOrdersLocked				= 'FALSE'
	SET @CurrentMonth				= (SELECT DATEPART(month, getdate()))
	SET @ProcessDate				= (SELECT ProcessDate FROM Process_Date WHERE DATEPART(month, ProcessDate) = @CurrentMonth)
	SET @CutoffTime					= (SELECT TOP 1 CAST(OrderCutoffHour AS varchar(2)) + ':' + CAST(OrderCutoffMinute AS varchar(2)) FROM Trading_Settings )
	SET @ProcessDateTime			= (SELECT @ProcessDate + CAST(@CutoffTime AS datetime) )
	SET @ProcessStatus_FK			= (SELECT ProcessStatus_FK FROM Process_Date WHERE ProcessDate = @ProcessDate)
	SET @PROCESS_STATUS_SUCCESS		= (SELECT ProcessStatus_PK FROM ProcessStatus_Types WHERE ProcessStatus = 'Success')
	
	-- It is passed the Cutoff Date and Time for this month's processing
	IF getdate() >= @ProcessDateTime
	BEGIN
		-- The processing has not yet been run successfully
		IF @ProcessStatus_FK <> @PROCESS_STATUS_SUCCESS
			SET @IsOrdersLocked = 'TRUE'
	END

	RETURN @IsOrdersLocked
END
GO
