@echo off
title Exam Seating System - Run with MySQL Connector
color 0A
cls

echo ============================================
echo   Exam Seating System - Running
echo ============================================
echo.

REM Find the MySQL connector JAR file
set CONNECTOR_JAR=
if exist "lib\mysql-connector-java-8.0.33.jar" (
    set CONNECTOR_JAR=lib\mysql-connector-java-8.0.33.jar
) else if exist "lib\mysql-connector-j-8.0.33.jar" (
    set CONNECTOR_JAR=lib\mysql-connector-j-8.0.33.jar
) else if exist "lib\*.jar" (
    for %%F in (lib\*.jar) do (
        set CONNECTOR_JAR=%%F
        goto :found
    )
)

:found

if defined CONNECTOR_JAR (
    echo [OK] Found MySQL connector: %CONNECTOR_JAR%
    echo.
) else (
    echo [ERROR] MySQL connector JAR not found in lib folder!
    echo Please download from: https://dev.mysql.com/downloads/connector/j/
    pause
    exit /b 1
)

REM Recompile the project
echo Compiling Java files...
javac -d bin -sourcepath src -cp "%CONNECTOR_JAR%" src/com/examseating/model/*.java src/com/examseating/util/*.java src/com/examseating/dao/*.java src/com/examseating/service/*.java src/com/examseating/ExamSeatingMain.java 2>compile_errors.txt

if %ERRORLEVEL% NEQ 0 (
    echo [WARNING] Compilation had errors. Check compile_errors.txt
    type compile_errors.txt
    echo.
    echo Continuing anyway...
) else (
    echo [OK] Compilation successful!
)

echo.
echo ============================================
echo   Starting Application
echo ============================================
echo.

java -cp "bin;%CONNECTOR_JAR%" com.examseating.ExamSeatingMain

echo.
echo ============================================
echo   Application Ended
echo ============================================
echo.
pause

