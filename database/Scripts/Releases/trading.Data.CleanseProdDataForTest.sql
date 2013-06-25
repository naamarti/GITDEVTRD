-----------------------------------------------------------------------------
-- Cleanse Prod data for test purposes
-- ** RUN ON NON_PROD ONLY **
-----------------------------------------------------------------------------

IF NOT EXISTS (SELECT * FROM TBS_Common..AppConfiguration WHERE EnvironmentName LIKE 'PRD%')
BEGIN
	-----------------------------------------------------------------------------
	-- Update All AppUser Passwords to "Password1"  **NON_PROD ONLY**
	-----------------------------------------------------------------------------
	UPDATE AppUser
	SET UserPassword = '70CCD9007338D6D81DD3B6271621B9CF9A97EA00'
	
	-----------------------------------------------------------------------------
	-- Update all real trader Usernames to testUser.pk to further anonymize
	-----------------------------------------------------------------------------
	UPDATE AppUser
	SET UserName = 'TestUser.'+convert(varchar,user_PK)
	WHERE UserName LIKE 'B%'  
	
	-----------------------------------------------------------------------------
	-- Update All quantities to ~ 100000 to keep anonymous
	-----------------------------------------------------------------------------
	UPDATE User_Balances
	SET  Quantity = 100000.25 + User_FK
		,Pending_Quantity = 100000.25 + User_FK
		
		
	-----------------------------------------------------------------------------
	-- Update All responses to 'b'
	-----------------------------------------------------------------------------
	UPDATE AppUser_ChallengeQuestion_relationship
	Set Response = 'b'
END

