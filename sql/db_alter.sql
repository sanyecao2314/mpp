grant update on ARCUST to report
grant update on ARCUSPRF to report
grant update on ARCUSMEM to report

grant EXECUTE on PS_UPDATE_NULL to report 


ALTER PROC PS_UPDATE_NULL
@tbName VARCHAR(100),
@fieldName VARCHAR(100),
@conditionStr VARCHAR(1000)
AS

DECLARE @str VARCHAR(8000)

SELECT @str = 'UPDATE ' + @tbname + ' SET ' + @fieldName + ' = NULL WHERE ' + @conditionStr

EXEC(@str)