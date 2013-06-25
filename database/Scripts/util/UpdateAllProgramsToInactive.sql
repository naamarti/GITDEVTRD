SET NOCOUNT ON;

UPDATE Programs SET Status_FK = (SELECT Status_PK FROM StatusTypes WHERE Code = 'INACTIVE')
go                                                               

