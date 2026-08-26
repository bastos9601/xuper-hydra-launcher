@echo off
echo ========================================
echo  COMPILANDO XUPER HYDRA LAUNCHER
echo ========================================
echo.

REM Verificar que Java este instalado
java -version >nul 2>&1
if errorlevel 1 (
    echo ERROR: Java no esta instalado
    pause
    exit /b 1
)

echo [OK] Java detectado
echo.

REM Verificar estructura
if not exist "app\src\main\java\com\xuperhydra\launcher\MainActivity.java" (
    echo ERROR: MainActivity.java no encontrado
    pause
    exit /b 1
)

if not exist "app\src\main\assets\login.html" (
    echo ERROR: login.html no encontrado
    pause
    exit /b 1
)

echo [OK] Archivos del proyecto verificados
echo.

echo IMPORTANTE:
echo ===========
echo Para compilar necesitas Android SDK.
echo.
echo ALTERNATIVAS:
echo.
echo 1. Usar GitHub Actions (automático, gratis)
echo 2. Usar AppOnline.io (online, sin instalación)
echo 3. Instalar Android SDK manualmente
echo.
echo ¿Quieres que te guíe con alguna opción? (S/N)
pause

