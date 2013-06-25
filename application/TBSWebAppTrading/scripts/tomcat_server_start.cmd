SET PATH=%DMS_HOME%\deployment\application\bin;%PATH%;
set CATALINA_HOME=%DMS_HOME%\deployment\apache-tomcat-7.0.27
cd %CATALINA_HOME%\bin\
call startup
