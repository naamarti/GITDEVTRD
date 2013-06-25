@echo off

:: ----------------------------------------
:: Set SQL Servers
:: ----------------------------------------
set DMS_SQLSERVER_DOTNET=10.160.0.34
set DMS_SQLSERVER1_DEV_A=DEVA-SQL01-PRI
set DMS_SQLSERVER1_DEV_B=DEVB-SQL01-PRI
set DMS_SQLSERVER1_DEV_C=DEVC-SQL01-PRI
set DMS_SQLSERVER1_DEV_D=DEVD-SQL01-PRI
set DMS_SQLSERVER1_DEV_E=DEVE-SQL01-PRI
set DMS_SQLSERVER1_QAT_A=QATA-SQL01-PRI
set DMS_SQLSERVER1_UAT_A=UATA-SQL01-PRI
set DMS_SQLSERVER1_UAT_B=UATB-SQL01-PRI
set DMS_SQLSERVER1_PRD_A_PRI=PRDA-SQL01-PRI
set DMS_SQLSERVER1_PRD_A_BAK=PRDA-SQL01-BAK
set DMS_SQLSERVER1_PRD_B_PRI=PRDB-SQL01-PRI
set DMS_SQLSERVER1_PRD_B_BAK=PRDB-SQL01-BAK

:: ----------------------------------------
:: Set JAVA App Servers
:: ----------------------------------------
set DMS_APPSERVER1_DEV_A=DEVA-APP01-PRI
set DMS_APPSERVER1_DEV_B=DEVB-APP01-PRI
set DMS_APPSERVER1_DEV_C=DEVC-APP01-PRI
set DMS_APPSERVER1_DEV_D=DEVD-APP01-PRI
set DMS_APPSERVER1_DEV_E=DEVE-APP01-PRI
set DMS_APPSERVER1_QAT_A=QATA-APP01-PRI
set DMS_APPSERVER1_UAT_A=UATA-APP01-PRI
set DMS_APPSERVER1_UAT_B=UATB-APP01-PRI
set DMS_APPSERVER1_PRD_A_PRI=PRDA-APP01-PRI
set DMS_APPSERVER1_PRD_A_BAK=PRDA-APP01-BAK
set DMS_APPSERVER1_PRD_B_PRI=PRDB-APP01-PRI
set DMS_APPSERVER2_PRD_B_PRI=PRDB-APP02-PRI
set DMS_APPSERVER1_PRD_B_BAK=PRDB-APP01-BAK

:: ----------------------------------------
:: Set JAVA App Service Name
:: ----------------------------------------
set DMS_APPNAME_DEV_A=TOMCAT7
set DMS_APPNAME_DEV_B=TOMCAT7
set DMS_APPNAME_DEV_C=TOMCAT7
set DMS_APPNAME_DEV_D=TOMCAT7
set DMS_APPNAME_DEV_E=TOMCAT7
set DMS_APPNAME_QAT_A=TOMCAT7
set DMS_APPNAME_UAT_A=TOMCAT7
set DMS_APPNAME_UAT_B=TOMCAT7
set DMS_APPNAME_PRD_A_PRI=TOMCAT7
set DMS_APPNAME_PRD_A_BAK=TOMCAT7
set DMS_APPNAME_PRD_B_PRI=TOMCAT7
set DMS_APPNAME_PRD_B_BAK=TOMCAT7

:: ----------------------------------------
:: Set .Net4 App Servers
:: ----------------------------------------
set DMS_NET4_APPSERVER1_DEV_A=DEVA-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_DEV_B=DEVB-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_DEV_C=DEVC-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_DEV_D=DEVD-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_DEV_E=DEVE-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_QAT_A=QATA-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_UAT_A=UATA-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_UAT_B=UATB-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_PRD_A_PRI=PRDA-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_PRD_A_BAK=PRDA-APP01-BAK.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_PRD_B_PRI=PRDB-APP01-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER2_PRD_B_PRI=PRDB-APP02-PRI.intranet.totalbanksolutions.com
set DMS_NET4_APPSERVER1_PRD_B_BAK=PRDB-APP01-BAK.intranet.totalbanksolutions.com


:: ----------------------------------------
:: Set Web Servers
:: ----------------------------------------
set DMS_WEBSERVER1_DEV_A=DEVA-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_DEV_B=DEVB-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_DEV_C=DEVC-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_DEV_D=DEVD-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_DEV_E=DEVE-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_QAT_A=QATA-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_UAT_A=UATA-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER2_UAT_A=UATA-WEB02-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_UAT_B=UATB-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER2_UAT_B=UATB-WEB02-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_PRD_A_PRI=PRDA-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER2_PRD_A_PRI=PRDA-WEB02-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER3_PRD_A_PRI=PRDA-WEB03-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER4_PRD_A_PRI=PRDA-WEB04-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_PRD_A_BAK=PRDA-WEB01-BAK.dmz.totalbanksolutions.com
set DMS_WEBSERVER2_PRD_A_BAK=PRDA-WEB02-BAK.dmz.totalbanksolutions.com
set DMS_WEBSERVER3_PRD_A_BAK=PRDA-WEB03-BAK.dmz.totalbanksolutions.com
set DMS_WEBSERVER4_PRD_A_BAK=PRDA-WEB04-BAK.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_PRD_B_PRI=PRDB-WEB01-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER2_PRD_B_PRI=PRDB-WEB02-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER3_PRD_B_PRI=PRDB-WEB03-PRI.dmz.totalbanksolutions.com
set DMS_WEBSERVER1_PRD_B_BAK=PRDB-WEB01-BAK.dmz.totalbanksolutions.com
set DMS_WEBSERVER2_PRD_B_BAK=PRDB-WEB02-BAK.dmz.totalbanksolutions.com
set DMS_WEBSERVER3_PRD_B_BAK=PRDB-WEB03-BAK.dmz.totalbanksolutions.com


:: ----------------------------------------
:: Set JAVA APP Server Share Directories
:: ----------------------------------------
set DMS_APPSERVER_SHARE_DEV_A=DMS.DEV.A/APP/JAVA
set DMS_APPSERVER_SHARE_DEV_B=tbs_autorecon_java
set DMS_APPSERVER_SHARE_DEV_C=tbs_autorecon_java.dev.c
set DMS_APPSERVER_SHARE_DEV_D=tbs_autorecon_java.dev.d
set DMS_APPSERVER_SHARE_DEV_E=tbs_autorecon_java.dev.e
set DMS_APPSERVER_SHARE_QAT_A=DMS.QAT.A/APP/JAVA
set DMS_APPSERVER_SHARE_UAT_A=DMS.UAT.A/APP/JAVA
set DMS_APPSERVER_SHARE_UAT_B=tbs_autorecon.uat.b
set DMS_APPSERVER_SHARE_PRD_A=tbs_autorecon.prd.a
set DMS_APPSERVER_SHARE_PRD_B=tbs_autorecon.prd.b


:: ----------------------------------------
:: Set .Net4 APP Server Share Directories
:: ----------------------------------------
set DMS_APPSERVER_NET4_SHARE_DEV_A=DMS.DEV.A/APP/NET4
set DMS_APPSERVER_NET4_SHARE_DEV_B=DMS.DEV.B/APP/NET4
set DMS_APPSERVER_NET4_SHARE_DEV_C=DMS.DEV.C/APP/NET4
set DMS_APPSERVER_NET4_SHARE_DEV_D=DMS.DEV.D/APP/NET4
set DMS_APPSERVER_NET4_SHARE_DEV_E=DMS.DEV.E/APP/NET4
set DMS_APPSERVER_NET4_SHARE_QAT_A=DMS.QAT.A/APP/NET4
set DMS_APPSERVER_NET4_SHARE_UAT_A=DMS.UAT.A/APP/NET4
set DMS_APPSERVER_NET4_SHARE_UAT_B=DMS.UAT.B/APP/NET4
set DMS_APPSERVER_NET4_SHARE_PRD_A=DMS.PRD.A/APP/NET4
set DMS_APPSERVER_NET4_SHARE_PRD_B=DMS.PRD.B/APP/NET4


:: ----------------------------------------
:: Set Web Server Share Directories for JAVA components
:: ----------------------------------------
set DMS_WEBSERVER_JAVA_SHARE_DEV_A=DMS.DEV.A/WEB/JAVA
set DMS_WEBSERVER_JAVA_SHARE_DEV_B=DMS.DEV.B/WEB/JAVA
set DMS_WEBSERVER_JAVA_SHARE_DEV_C=DMS.DEV.C/WEB/JAVA
set DMS_WEBSERVER_JAVA_SHARE_DEV_D=DMS.DEV.D/WEB/JAVA
set DMS_WEBSERVER_JAVA_SHARE_DEV_E=DMS.DEV.E/WEB/JAVA
set DMS_WEBSERVER_JAVA_SHARE_QAT_A=DMS.QAT.A/WEB/JAVA
set DMS_WEBSERVER_JAVA_SHARE_UAT_A=DMS.UAT.A/WEB/JAVA
set DMS_WEBSERVER_JAVA_SHARE_UAT_B=DMS.UAT.B/WEB/JAVA
set DMS_WEBSERVER_JAVA_SHARE_PRD_A=DMS.PRD.A/WEB/JAVA
set DMS_WEBSERVER_JAVA_SHARE_PRD_B=DMS.PRD.B/WEB/JAVA



:: ----------------------------------------
:: Set Web Server Share Directories for .Net2 components
:: ----------------------------------------
set DMS_WEBSERVER_NET_SHARE_DEV_A=DMS.DEV.A/WEB/NET2
set DMS_WEBSERVER_NET_SHARE_DEV_B=DMS.DEV.B/WEB/NET2
set DMS_WEBSERVER_NET_SHARE_DEV_C=DMS.DEV.C/WEB/NET2
set DMS_WEBSERVER_NET_SHARE_DEV_D=DMS.DEV.D/WEB/NET2
set DMS_WEBSERVER_NET_SHARE_DEV_E=DMS.DEV.E/WEB/NET2
set DMS_WEBSERVER_NET_SHARE_QAT_A=DMS.QAT.A/WEB/NET2
set DMS_WEBSERVER_NET_SHARE_UAT_A=DMS.UAT.A/WEB/NET2
set DMS_WEBSERVER_NET_SHARE_UAT_B=DMS.UAT.B/WEB/NET2
set DMS_WEBSERVER_NET_SHARE_PRD_A=DMS.PRD.A/WEB/NET2
set DMS_WEBSERVER_NET_SHARE_PRD_B=DMS.PRD.B/WEB/NET2


:: ----------------------------------------
:: Set Web Server Share Directories for .Net4 components
:: ----------------------------------------
set DMS_WEBSERVER_NET4_SHARE_DEV_A=DMS.DEV.A/WEB/NET4
set DMS_WEBSERVER_NET4_SHARE_DEV_B=DMS.DEV.B/WEB/NET4
set DMS_WEBSERVER_NET4_SHARE_DEV_C=DMS.DEV.C/WEB/NET4
set DMS_WEBSERVER_NET4_SHARE_DEV_D=DMS.DEV.D/WEB/NET4
set DMS_WEBSERVER_NET4_SHARE_DEV_E=DMS.DEV.E/WEB/NET4
set DMS_WEBSERVER_NET4_SHARE_QAT_A=DMS.QAT.A/WEB/NET4
set DMS_WEBSERVER_NET4_SHARE_UAT_A=DMS.UAT.A/WEB/NET4
set DMS_WEBSERVER_NET4_SHARE_UAT_B=DMS.UAT.B/WEB/NET4
set DMS_WEBSERVER_NET4_SHARE_PRD_A=DMS.PRD.A/WEB/NET4
set DMS_WEBSERVER_NET4_SHARE_PRD_B=DMS.PRD.B/WEB/NET4

:: ----------------------------------------
:: Set .Net2 URL baseaddress
:: ----------------------------------------
set DMS_URLBASEADDR_NET2_DEV_A=deva.tbslink.net
set DMS_URLBASEADDR_NEt2_DEV_B=devb.tbslink.net
set DMS_URLBASEADDR_NET2_DEV_C=devc.tbslink.net
set DMS_URLBASEADDR_NET2_DEV_D=devd.tbslink.net
set DMS_URLBASEADDR_NET2_DEV_E=deve.tbslink.net
set DMS_URLBASEADDR_NET2_QAT_A=qata.tbslink.net
set DMS_URLBASEADDR_NET2_UAT_A=uata.tbslink.net
set DMS_URLBASEADDR_NET2_UAT_B=uatb.tbslink.net
set DMS_URLBASEADDR_NET2_PRD_A=tbslink.com
set DMS_URLBASEADDR_NET2_PRD_B=tbslink.com

:: ----------------------------------------
:: Set .Net4 webservices baseaddress
:: ----------------------------------------
set DMS_BASEADDR_NET4_DEV_A=devawebsrvcs.tbslink.net:1443
set DMS_BASEADDR_NET4_DEV_B=devbwebsrvcs.tbslink.net:1443
set DMS_BASEADDR_NET4_DEV_C=devcwebsrvcs.tbslink.net:1443
set DMS_BASEADDR_NET4_DEV_D=devdwebsrvcs.tbslink.net:1443
set DMS_BASEADDR_NET4_DEV_E=devewebsrvcs.tbslink.net:1443
set DMS_BASEADDR_NET4_QAT_A=qatawebsrvcs.tbslink.net:1443
set DMS_BASEADDR_NET4_UAT_A=uatawebsrvcs.tbslink.net:1443
set DMS_BASEADDR_NET4_UAT_B=uatbwebsrvcs.tbslink.net:1443
set DMS_BASEADDR_NET4_PRD_A=prdawebsrvcs.tbslink.net:1443
set DMS_BASEADDR_NET4_PRD_B=prdbwebsrvcs.tbslink.com:1443


:: ----------------------------------------
:: Set DMS JAVA APP Log Base Directory
:: ----------------------------------------
set DMS_LOG_BASEADDR_JAVA_LOCAL=c:/tbs_autorecon/deployment/application
set DMS_LOG_BASEADDR_JAVA_DEV_A=D:/DMS/DEV.A/APP/JAVA/deployment/application
set DMS_LOG_BASEADDR_JAVA_DEV_B=c:/tbs_autorecon/deployment/application
set DMS_LOG_BASEADDR_JAVA_DEV_C=c:/tbs_autorecon/deployment/application
set DMS_LOG_BASEADDR_JAVA_DEV_D=c:/tbs_autorecon/deployment/application
set DMS_LOG_BASEADDR_JAVA_DEV_E=c:/tbs_autorecon/deployment/application
set DMS_LOG_BASEADDR_JAVA_QAT_A=E:/DMS/QATA/APP/JAVA/deployment/application
set DMS_LOG_BASEADDR_JAVA_UAT_A=E:/DMS/UATA/APP/JAVA/deployment/application
set DMS_LOG_BASEADDR_JAVA_UAT_B=c:/tbs_autorecon/deployment/application
set DMS_LOG_BASEADDR_JAVA_PRD_A=c:/tbs_autorecon/deployment/application
set DMS_LOG_BASEADDR_JAVA_PRD_B=c:/tbs_autorecon/deployment/application


:: ----------------------------------------
:: Environments
:: ----------------------------------------
set DMS_ENV_ORDER_LOCAL=1
set DMS_ENV_ORDER_DEV_TRADING=2
set DMS_ENV_ORDER_DEV=2
set DMS_ENV_ORDER_QA_STAGE=3
set DMS_ENV_ORDER_QA=4
set DMS_ENV_ORDER_UAT=5
set DMS_ENV_ORDER_PROD_TRADING=6
set DMS_ENV_ORDER_PROD=6
