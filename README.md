# Xuper Hydra Launcher

Launcher APK para Xuper Hydra con sistema de login de 6 dígitos y validación Supabase.

## 🚀 Compilación Automática

Este proyecto usa GitHub Actions para compilación automática en la nube.

### Pasos para compilar:

1. **Crear repositorio en GitHub**
   - Ve a https://github.com/new
   - Nombre: `xuper-hydra-launcher`
   - Marca como Privado (opcional)
   - Click en "Create repository"

2. **Subir el proyecto**
   ```bash
   cd PROYECTO_LAUNCHER
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/TU_USUARIO/xuper-hydra-launcher.git
   git push -u origin main
   ```

3. **Esperar compilación**
   - Ve a tu repositorio
   - Click en "Actions"
   - Espera ~3 minutos
   - Verás el workflow "Build Xuper Hydra Launcher APK"

4. **Descargar APK**
   - Click en el workflow completado (✅)
   - Baja a "Artifacts"
   - Click en "XuperHydraLauncher"
   - Descarga el ZIP
   - Extrae `XuperHydraLauncher.apk`

## 📱 Características

- ✅ Login de 6 dígitos
- ✅ Validación con Supabase Edge Function
- ✅ Control de sesiones
- ✅ Manejo de expiración
- ✅ Control de límite de dispositivos
- ✅ Sistema de notificaciones
- ✅ Logout manteniendo logo presionado 3 segundos

## 🔧 Estructura

```
PROYECTO_LAUNCHER/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── AndroidManifest.xml
│   │       ├── java/com/xuperhydra/launcher/
│   │       │   └── MainActivity.java
│   │       ├── assets/
│   │       │   └── login.html
│   │       └── res/
│   │           └── values/
│   │               └── strings.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── .github/
    └── workflows/
        └── build.yml

```

## 🔐 API

**Endpoint**: `https://sxhupinyysffokplogvj.supabase.co/functions/v1/validar-codigo`

**Método**: POST

**Content-Type**: `application/x-www-form-urlencoded`

**Parámetros**:
- `code`: Código de 6 dígitos
- `device_id`: ID único del dispositivo

**Respuesta exitosa**:
```json
{
  "ok": true,
  "reason": "",
  "expires_at": "2026-12-31T23:59:59.000Z",
  "days_left": 180,
  "notif_id": 1,
  "notif_title": "Bienvenido",
  "notif_message": "Gracias por usar Xuper Hydra"
}
```

## 📦 Instalación

1. Instala primero: `xuper hydra movil v4 (principal oculta).apk`
2. Instala después: `XuperHydraLauncher.apk`
3. Abre el launcher desde el escritorio
4. Ingresa tu código de 6 dígitos
5. La app principal se abrirá automáticamente

## 🛠️ Desarrollo Local

Si quieres compilar localmente necesitas:

- Java JDK 11+
- Android SDK
- Gradle 7.5+

```bash
./gradlew assembleDebug
```

APK generado en: `app/build/outputs/apk/debug/app-debug.apk`

## 📄 Licencia

Privado - Solo para uso autorizado
