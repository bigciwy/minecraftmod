@echo off
REM Direct Java compilation without Gradle

echo Compiling Java files directly...

REM Assuming we have dependencies downloaded
set CLASSPATH=.

cd src\main\java

REM Try to compile with javac
javac -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: javac not found. Install JDK (not just JRE)
    exit /b 1
)

echo Created build directories...
mkdir ..\..\..\..\build\classes 2>nul

REM Compile Java files
echo Compiling Java files...
javac -d ..\..\..\..\build\classes -cp . com\example\examplemod\*.java com\example\examplemod\client\*.java com\example\examplemod\client\renderer\*.java 2>&1

if errorlevel 0 (
    echo Compilation successful!
    echo Classes compiled to build\classes
) else (
    echo Compilation failed - see errors above
    exit /b 1
)

cd ..\..\..
