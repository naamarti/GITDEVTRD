package com.totalbanksolutions.framework.bean.database.table;

import com.totalbanksolutions.framework.bean.database.DatabaseNames;
import com.totalbanksolutions.framework.bean.util.AbstractDatabaseBean;
import com.totalbanksolutions.framework.enums.DatabaseDataType;

/**
 * =================================================================================================
 * Created:   VC
 * Modified:  10 Aug 2011 VC  #984: Direct Settlement - Add DMS checklist step to generate direct settlement report
 *            05 Oct 2011 VC #1022: Add DI Customer Balances Summary report (RG#101) to Universal Reports Checklist
 *            09 Nov 2011 VC #1054: Eliminate omnibus MMDA withdrawals by reducing NOW balances where possible
 *			  06 Feb 2012 NM #1274: added wire movement report process type
 *			  23 Apr 2012 VC #1495: Forecast - separate waterfall forecast & generate reports steps from normal forecast
 *            23 Apr 2012 VC  #966: All Programs / Bank Centric checklist - add First Niagara quarterly report
 *            29 Apr 2012 VC #1549: Add step to Universal Checklist - Recon Open Items report
 *            07 May 2012 NM #1547: add Program Summary Report for email sender            
 *            22 Aug 2012 VC  #688: new DMS Universal Checklist step - Month End DI Customer Balances Detail (RG #107)
 *            23 Aug 2012 VC #1826: Add DMS Universal Checklist step to generate Bank rate report 
 *            23 Aug 2012 VC #1827: Add DMS checklist step to Universal checklist for generation of Daily Summary report
 *            24 Aug 2012 NM #1838: find reports by envelope            
 *            01 Sep 2012 VC #1894: Clearview average balance file
 *
 * Bean for the DailyProcessType table.
 * 
 * =================================================================================================
 */
public class DailyProcessType extends AbstractDatabaseBean<DailyProcessType.Field> 
{
	public static String DATABASE_NAME = DatabaseNames.TBS_Common.name();
	public static String TABLE_NAME = "DailyProcessType";
	public static String DATABASE_TABLE_NAME = DATABASE_NAME + ".." + TABLE_NAME;
	
	public enum Values
	{
		
		 FILE_GENERATE_ACCOUNT_AVERAGE_BALANCES     ( "FileGenerateAccountAverageBalances"              )
		,FILE_GENERATE_ACCOUNTS_1099                ( "FileGenerateAccounts1099"                        )
		,FILE_GENERATE_ACCRUED_INTEREST             ( "FileGenerateAccruedInterest"                     ) 
		,FILE_GENERATE_AFFILIATED_BANK_REVENUE      ( "FileGenerateAffiliatedBankRevenue"               ) 
		,FILE_GENERATE_BALANCES                     ( "FileGenerateBalances"                            )  
		,FILE_GENERATE_BALANCE_SUMMARY_AND_FEES     ( "FileGenerateBalanceSummaryAndFeeReport" 			)  
		,FILE_GENERATE_BANK_BALANCES                ( "FileGenerateBankBalances"                        )
		,FILE_GENERATE_CONFIRMS                     ( "FileGenerateConfirms"                            )
		,FILE_GENERATE_CONSOLIDATION_ACCOUNT_REPORT ( "FileGenerateConsolidationAccountReport"          )
		,FILE_GENERATE_CONSOLIDATION_GROUP_REPORT   ( "FileGenerateConsolidationGroupReport"            )
		,FILE_GENERATE_FEES                         ( "FileGenerateFees"                                )
		,FILE_GENERATE_FORECAST_REPORT				( "FileGenerateForecastReport"                      )
		,FILE_GENERATE_FORECAST_WATERFALL_REPORT	( "FileGenerateForecastWaterfallReport"             )
		,FILE_GENERATE_INITIAL_DEPOSITS             ( "FileGenerateInitialDeposits"                     )
		,FILE_GENERATE_NONAFFILIATED_BANK_REVENUE   ( "FileGenerateNonAffiliatedBankRevenue"            ) 
		,FILE_GENERATE_PE_BNK_BAL_BY_PROD_REPORT	( "FileGeneratePEDailyBankBalanceReport"   			)
		,FILE_GENERATE_POSITIONS                    ( "FileGeneratePositions"                           ) 
		,FILE_GENERATE_PROGRAM_BANKS                ( "FileGenerateProgramBanks"                        )
		,FILE_GENERATE_REGISTRATION_ERRORS          ( "FileGenerateRegistrationErrors"                  )
		,FILE_GENERATE_SI_BALANCE_REPORT            ( "FileGenerateSIBalanceReport"	                    )
		,FILE_GENERATE_STATEMENTS                   ( "FileGenerateStatements"                          ) 
		,FILE_GENERATE_WIRES                        ( "FileGenerateWires"                               )
		,FILE_GENERATE_INTERNAL_LOG                 ( "FileGenerateInternalLog"                         )
		,FILE_GENERATE_FULL_INTERNAL_LOG			( "FileGenerateFullInternalLog"                     )
		,FILE_GENERATE_STATEMENT_CONTENTS           ( "FileGenerateStatementContents"                   )
		,FILE_GENERATE_FEE_SUMMARY                  ( "FileGenerateFeeSummary"                          )
		,FILE_GENERATE_DELETE_TRANSACTIONS        	( "FileGenerateTransactionDeletions"              	)
		
		,FILE_LOAD_ACCOUNT_BALANCES                 ( "FileLoadAccountBalances"                         )
		,FILE_LOAD_ACCOUNTS                         ( "FileLoadAccounts"                                )
		,FILE_LOAD_PRODUCT_CODES					( "FileLoadProductCodes"							)		
		,FILE_LOAD_BANK_OPTOUTS                     ( "FileLoadBankOptOuts"                             ) 
		,FILE_LOAD_BANK_OPTOUTS_MANUAL              ( "FileLoadBankOptOutsManual"                       ) 
		,FILE_LOAD_MANUAL_ACCOUNTS                  ( "FileLoadManualAccounts"                          )
		,FILE_LOAD_MANUAL_UNIVERSAL_ACCOUNTS		( "FileLoadManualUniversalAccounts"					)			
		,FILE_LOAD_MANUAL_TRANSACTIONS              ( "FileLoadManualTransactions"                      ) 
		,FILE_LOAD_DELETE_TRANSACTIONS              ( "FileLoadDeleteTransactions"                      ) 		
		,FILE_LOAD_TRANSACTIONS                     ( "FileLoadTransactions"                            )
		,FILE_LOAD_CHANGE_ACCOUNT_NUMBER			( "FileLoadChangeAccountNumber"                     )

		,PROCESS_ALLOCATE_MMDA_NOW_BALANCES         ( "ProcessAllocateMMDANOWBalances"                  )
		,PROCESS_ALLOCATE_MMDA_NOW_BANK_BALANCES    ( "ProcessAllocateMMDANOWBankBalances"              )
		,PROCESS_ALLOCATE_REDUCE_NOW_BALANCES       ( "ProcessAllocateReduceNOWBalances"                )
		,PROCESS_CALCULATE_INTEREST_AND_FEES        ( "ProcessCalculateInterestAndFees"                 )
		,PROCESS_CALCULATE_EARLY_WIRE				( "ProcessCalculatePreliminaryEarlyWire"			)	
		,PROCESS_CASH_INTEREST_PAYMENTS             ( "ProcessCashInterestPayments"                     )
		,PROCESS_CREATE_ACCOUNT_TAXID_GROUPS 		( "ProcessCreateAccountTaxIDGroups"              	)
		,PROCESS_CREATE_RESTORE_POINT 		        ( "ProcessCreateRestorePoint"                       )		
		,PROCESS_COMMIT_TRANSACTIONS                ( "ProcessCommitTransactions"                       )
		,PROCESS_CONSOLIDATE_ACCOUNTS               ( "ProcessConsolidationOfAccounts"                  )
		,PROCESS_FORECAST                           ( "ProcessForecastSettlementNumbers"                )
		,PROCESS_FORECAST_WATERFALL                 ( "ProcessForecastWaterfall"                        )
		,PROCESS_PUBLISH_DATA_TO_AUTORECON          ( "ProcessPublishDataToAutoRecon"                   )
		,PROCESS_ROLL_TO_NEXT_DAY                   ( "ProcessRollToNextDay"                            )

		,PROCESS_EMAIL_DEPOSIT_ACTIVITY_REPORTS		( "EmailDepositActivityReports"                     )
		,PROCESS_EMAIL_OMNIBUS_ACTIVTY_REPORTS  	( "EmailOmnibusActivityReports"                     )		
		,PROCESS_EMAIL_OFFSET_WINDOW_INSTRUCTIONS  	( "EmailMMDAOffsetWindowInstructions"               )
		,PROCESS_EMAIL_TRANSFER_WINDOW_INSTRUCTIONS ( "EmailMMDANOWTransferWindowInstructions"          )		
		,PROCESS_EMAIL_OVER_FDIC_REPORT				( "EmailReportOverFDIC"                     		)
		,PROCESS_EMAIL_OVER_FDIC_DETAIL_REPORT		( "EmailReportOverFDICAcctDetail"                   )
		,PROCESS_EMAIL_ACTIVITY_DETAIL_REPORT		( "EmailActivityDetailReport"                     	)
		,PROCESS_EMAIL_ACTIVITY_SUMMARY_REPORT		( "EmailActivitySummaryReport"                     	)
		,PROCESS_EMAIL_ACTIVITY_EXCEPTION_REPORT	( "EmailActivityExceptionsReport"                   )
		,PROCESS_EMAIL_EXPECTED_SETTLEMENT_REPORT	( "EmailExpectedSettlementReport"                   )
		,PROCESS_EMAIL_PROGRAM_SUMMARY_REPORT	    ( "EmailProgramSummaryReport"                       )
		,PROCESS_EMAIL_SWEEP_SETTLEMENT_ENVELOPE    ( "EmailSweepSettlementEnvelope"                    )


		,REPORT_ACCOUNT_STRATIFICATION				( "ReportAccountStratification"                     )
		,REPORT_ACTIVITY_DETAIL                     ( "ReportActivityDetail"                            )
		,REPORT_ACTIVITY_EXCEPTIONS                 ( "ReportActivityExceptions"                        )
		,REPORT_ACTIVITY_SUMMARY                    ( "ReportActivitySummary"                           )
		,REPORT_ALL_PROGRAM_SUMMARY                 ( "ReportAllProgramSummary"                         )
		,REPORT_BALANCE_SUMMARY_BY_DI               ( "ReportBalanceSummaryByDI"                        ) 
		,REPORT_BALANCE_SUMMARY_BY_PRODUCT          ( "ReportBalanceSummaryByProduct"                   )
		,REPORT_BAL_SUM_BY_PRD_BANK_DETAILS         ( "ReportBalSumByPrdBankDetails"                   	)
		,REPORT_BANK_BALANCES                       ( "ReportBankBalances"                              ) 
		,REPORT_BANK_BALANCES_FOR_RECON				( "ReportBankBalancesForRecon"						)						
		,REPORT_CUSTOMER_ACCOUNT_DETAIL				( "ReportCustomerAccountDetail"						)
		,REPORT_DEPOSIT_ACTIVITY              		( "ReportDepositActivity"                       	) 
		,REPORT_DI_CUSTOMER_BALANCES_DETAIL			( "ReportDICustomerBalancesDetail"                  )
		,REPORT_DI_CUSTOMER_BALANCES_SUMMARY		( "ReportDICustomerBalancesSummary"                 )
		,REPORT_DI_TRANCHE_CAPACITY					( "ReportDITrancheCapacity"                         )
		,REPORT_DIRECT_SETTLEMENT_WIRE_ACTIVITY		( "ReportDirectSettlementWireActivity"              ) 
		,REPORT_FEES_RBC_AFFILIATED_BANKS			( "ReportFeesRBCAffiliatedBanks"					)
		,REPORT_FEES_RBC_NONAFFILIATED_BANKS		( "ReportFeesRBCNonAffiliatedBanks"					)
		,REPORT_FEES_WIRE_MOVEMENT					( "ReportFeesWireMovement"							)
		,REPORT_FEES_SI								( "ReportFeesSI"									)				
		,REPORT_MMDA_OFFSET_WINDOW_INSTR            ( "ReportMMDAOffsetWindowInstructions"              )
		,REPORT_MMDA_NOW_TICK_COUNT                 ( "ReportMMDANOWTickCount"                          )
		,REPORT_MMDA_NOW_TRANSFER_WINDOW_INSTR      ( "ReportMMDANOWTransferWindowInstructions"         )
		,REPORT_MONTHLY_CALL                        ( "ReportMonthlyCall"                               )
		,REPORT_OMNIBUS_ACTIVITY                    ( "ReportOmnibusActivity"                           )
		,REPORT_OVER_FDIC 	                        ( "ReportOverFDIC"	                                )
		,REPORT_OVER_FDIC_ACCT_DETAIL				( "ReportOverFDICAcctDetail"                        )
		,REPORT_PROGRAM_SUMMARY                     ( "ReportProgramSummary"                            )
		,REPORT_RECON_OPEN_ITEMS_ACCRUALS			( "ReportReconOpenItemsAccruals"                    )
		,REPORT_RECON_OPEN_ITEMS_BALANCES			( "ReportReconOpenItemsBalances"                    )
		,REPORT_SETTLEMENT                          ( "ReportSettlement"                                )
		,ASYNC_REPORT_GENERATION                    ( "AsyncReportGeneration"                           )

		,REQUEST_FTP_FILE		                    ( "CheckFTPFileAvailability" 	                    )
		,REQUEST_FTP_FILE_ACCOUNTS                  ( "FileRequestAndCheckAccounts" 	                )
		,REQUEST_FTP_FILE_ACCOUNT_BALANCES          ( "FileRequestAndCheckAccountBalances"              )
		,REQUEST_FTP_FILE_TRANSACTIONS              ( "FileRequestAndCheckTransactions"	                )
		,REQUEST_FTP_FILE_OPTOUTS                   ( "FileRequestAndCheckOptOuts"	                    )
		
		,REQUEST_SEND_FTP_FILE_BALANCES            ( "FileRequestSendBalancesFile"                      )
		,REQUEST_SEND_FTP_FILE_BANK_BALANCES       ( "FileRequestSendBankBalancesFile"                  )
		,REQUEST_SEND_FTP_FILE_ACCRUED_INTEREST    ( "FileRequestSendAccruedInterestFile"               )
		,REQUEST_SEND_FTP_FILE_STATEMENTS          ( "FileRequestSendStatementsFile"	                )
		,REQUEST_SEND_FTP_FILE_WIRES               ( "FileRequestSendWireFile"	                        )
		
		,VALIDATE_SEND_FTP_FILE_BALANCES            ( "FileValidateSendBalancesFile"                    )
		,VALIDATE_SEND_FTP_FILE_BANK_BALANCES       ( "FileValidateSendBankBalancesFile"                )
		,VALIDATE_SEND_FTP_FILE_ACCRUED_INTEREST    ( "FileValidateSendAccruedInterestFile"             )
		,VALIDATE_SEND_FTP_FILE_STATEMENTS          ( "FileValidateSendStatementsFile"	                )
		,VALIDATE_SEND_FTP_FILE_WIRES               ( "FileValidateSendWireFile"	                    )
		;
		private Values(String id) { this.id = id; }		
		private String id;
		public String getId() { return id; }
	}

	public enum Field
	{
		 DESCRIPTION
		,MENU_NAME
		,OPERATION_TYPE_ID
		,PROCESS_TYPE
		,PROCESS_TYPE_ID
		;
	}

	public DailyProcessType()
	{
		super(DATABASE_NAME, TABLE_NAME);
		// --------------------------------------------------------------------------------------------------------------------------------
		//					ConstantName				Database_FieldName			Database_DataType					Size	IsIdentity
		// --------------------------------------------------------------------------------------------------------------------------------		
		super.addField(Field.DESCRIPTION,				"Description",				DatabaseDataType.CHAR,				255,	false);
		super.addField(Field.MENU_NAME,					"MenuName",					DatabaseDataType.CHAR,				100,	false);
		super.addField(Field.OPERATION_TYPE_ID,			"OperationType_FK",			DatabaseDataType.DECIMAL_LONGINT,	0,		false);
		super.addField(Field.PROCESS_TYPE,				"ProcessType",				DatabaseDataType.CHAR,				100,	false);
		super.addField(Field.PROCESS_TYPE_ID,			"ProcessType_PK",			DatabaseDataType.DECIMAL_LONGINT,	0,		true);
	}

	// Getters
	public long getOperationTypeId()
	{
		return this.getField(Field.OPERATION_TYPE_ID).getLongValue();
	}

	public String getProcessType()
	{
		return this.getField(Field.PROCESS_TYPE).getStringValue();
	}
	
	public long getProcessTypeId()
	{
		return this.getField(Field.PROCESS_TYPE_ID).getLongValue();
	}
}
