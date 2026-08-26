package com.xuperhydra.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    private WebView webView;
    private static final String PREFS_NAME = "XuperHydraPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EXPIRES_AT = "expiresAt";
    private static final String MAIN_APP_PACKAGE = "com.msandroid.mobile";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Verificar si la app principal está instalada
        if (!isAppInstalled(MAIN_APP_PACKAGE)) {
            Toast.makeText(this, "Instalando componentes necesarios...", Toast.LENGTH_LONG).show();
            // Aquí podrías instalar automáticamente o mostrar instrucciones
        }
        
        // Verificar si ya está logueado y no ha expirado
        if (isValidLogin()) {
            launchMainApp();
            return;
        }
        
        // Crear WebView para el login
        webView = new WebView(this);
        setContentView(webView);
        
        // Configurar WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        
        // Agregar interfaz JavaScript
        webView.addJavascriptInterface(new WebAppInterface(), "AndroidInterface");
        
        // Configurar WebViewClient
        webView.setWebViewClient(new WebViewClient());
        
        // Cargar login desde assets
        webView.loadUrl("file:///android_asset/login.html");
    }
    
    private boolean isAppInstalled(String packageName) {
        try {
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    
    private boolean isValidLogin() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean(KEY_IS_LOGGED_IN, false);
        String expiresAt = prefs.getString(KEY_EXPIRES_AT, "");
        
        if (!isLoggedIn || expiresAt.isEmpty()) {
            return false;
        }
        
        // Verificar si no ha expirado
        try {
            // Parsear fecha ISO 8601
            long expiryTime = parseISO8601(expiresAt);
            long currentTime = System.currentTimeMillis();
            return currentTime < expiryTime;
        } catch (Exception e) {
            return false;
        }
    }
    
    private long parseISO8601(String dateString) {
        try {
            // Formato: 2026-12-31T23:59:59.000Z
            dateString = dateString.replace("Z", "+00:00");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                return java.time.Instant.parse(dateString.replace("+00:00", "Z")).toEpochMilli();
            } else {
                // Para versiones antiguas de Android
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
                return sdf.parse(dateString.replace("+00:00", "Z")).getTime();
            }
        } catch (Exception e) {
            return 0;
        }
    }
    
    private void saveLoginState(String expiresAt) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putString(KEY_EXPIRES_AT, expiresAt);
        editor.apply();
    }
    
    private void launchMainApp() {
        try {
            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage(MAIN_APP_PACKAGE);
            
            if (intent != null) {
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Error: Componente principal no encontrado. Reinstala la aplicación.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error al iniciar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
    public class WebAppInterface {
        @JavascriptInterface
        public void onLoginSuccess(String expiresAt) {
            saveLoginState(expiresAt);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    launchMainApp();
                }
            });
        }
        
        @JavascriptInterface
        public void closeWebView() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    launchMainApp();
                }
            });
        }
    }
    
    @Override
    public void onBackPressed() {
        // No permitir salir con botón atrás
        Toast.makeText(this, "Debes ingresar un código válido para continuar", Toast.LENGTH_SHORT).show();
    }
}
