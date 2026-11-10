
@echo off
echo ========================================
echo DECMS AUTOMATIC DEPLOYMENT
echo ========================================

set "PROJECT=%~dp0"
set "TOMCAT=C:\Users\Admin\Downloads\apache-tomcat-10.1.61-windows-x64\apache-tomcat-10.1.61"
set "DEPLOY=%TOMCAT%\webapps\decms"

echo.
echo [1/3] Compiling Java Servlets...

dir /s /b "%PROJECT%src\*.java" > "%PROJECT%sources.txt"

javac -cp "%TOMCAT%\lib\servlet-api.jar" -d "%DEPLOY%\WEB-INF\classes" @"%PROJECT%sources.txt"

if errorlevel 1 (
    echo.
    echo JAVA COMPILATION FAILED!
    pause
    exit /b 1
)

echo.
echo [2/3] Copying JSP, CSS and WebContent files...

robocopy "%PROJECT%WebContent" "%DEPLOY%" /E /NFL /NDL /NJH /NJS

echo.
echo [3/3] Deployment completed successfully!
echo.
echo Open: http://localhost:8081/decms/
echo.

pause
