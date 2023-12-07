@echo off
chcp 65001 > nul
if [%1]==[] (
    echo "You must specify a server"
    goto :Exit
) else (
    goto :Main
)

:compile
echo "Идет компиляция.."
javac -d class ./src/utilities/*.java -encoding utf-8
javac -cp "lib/jade.jar;lib/json-simple-1.1.1.jar;class/" -d class ./src/*.java -encoding utf-8
goto :eof

:clean
echo "Cleaning up.."
rd /s /q class
rmdir /s /q class
goto :eof

:Main
if %1==compile (
        call :compile
) else if %1==clean (
        call :clean
)

:Exit