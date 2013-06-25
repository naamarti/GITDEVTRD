SET NOCOUNT ON;

UPDATE TBS_Common..Programs SET Status_FK = (SELECT Status_PK FROM TBS_Common..StatusTypes WHERE Code = 'ACTIVE')
WHERE ProgramDatabaseName = DB_NAME()
go                                                               

