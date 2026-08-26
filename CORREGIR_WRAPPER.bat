@echo off
echo ========================================
echo  DESCARGANDO GRADLE WRAPPER
echo ========================================
echo.

cd /d "%~dp0"

REM Crear carpeta gradle\wrapper si no existe
if not exist "gradle\wrapper" mkdir gradle\wrapper

echo Descargando gradle-wrapper.jar...
powershell -Command "& {Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/gradle/gradle/master/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar'}"

if exist "gradle\wrapper\gradle-wrapper.jar" (
    echo [OK] gradle-wrapper.jar descargado
    echo.
    echo Ahora ejecuta:
    echo   git add gradle/wrapper/gradle-wrapper.jar
    echo   git commit -m "Agregar gradle wrapper jar"
    echo   git push
    echo.
    echo Y GitHub Actions recompilara automaticamente
) else (
    echo [ERROR] No se pudo descargar el archivo
)

pause
