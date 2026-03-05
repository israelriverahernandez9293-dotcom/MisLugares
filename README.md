# MisLugares Android Setup

Base de configuración para Android Studio con:
- Firebase Authentication (correo/contraseña).
- Integración con Google Photos Library API vía Retrofit.

## 1) Archivos que debes agregar manualmente

1. Descarga `google-services.json` desde Firebase Console.
2. Copia el archivo en:
   ```
   app/google-services.json
   ```
3. Verifica que el `applicationId` en `app/build.gradle.kts` coincida con Firebase.

> `google-services.json` está ignorado por git para evitar exponer secretos.

## 2) Firebase Authentication

En Firebase Console:
1. Ve a **Authentication > Sign-in method**.
2. Habilita **Email/Password**.

Código principal:
- `FirebaseAuthRepository` centraliza login, registro y logout.
- `MisLugaresApp` inicializa Firebase al arrancar la app.

## 3) Google Photos API

### 3.1 Configuración en Google Cloud
1. Habilita **Photos Library API**.
2. Configura pantalla de consentimiento OAuth.
3. Crea credenciales OAuth (Android).
4. Añade SHA-1/SHA-256 del keystore de debug/release.

### 3.2 Scope recomendado
Usa scopes mínimos según tu necesidad, por ejemplo:
- `https://www.googleapis.com/auth/photoslibrary.readonly`

### 3.3 Flujo sugerido (buenas prácticas)
1. Usuario inicia sesión en app (Firebase email/password).
2. Si se requiere Google Photos, solicita OAuth de Google por separado.
3. Obtén access token temporal.
4. Llama `GooglePhotosRepository.fetchAlbums(accessToken)`.
5. Nunca guardes tokens en texto plano.

## 4) Estructura propuesta para mantenimiento

```text
app/src/main/java/com/mislugares/
  auth/   -> login, registro, sesión (Firebase)
  photos/ -> cliente API Google Photos
  core/   -> inicialización global y utilidades base
  ui/     -> pantallas/viewmodels
```

Esta separación ayuda a localizar bugs rápido por dominio.

## 5) Checklist rápida

- [ ] `app/google-services.json` agregado localmente.
- [ ] Email/Password habilitado en Firebase.
- [ ] Photos Library API habilitada en GCP.
- [ ] SHA-1/SHA-256 cargadas.
- [ ] Scope correcto definido para Google Photos.
- [ ] Manejo de errores de red y autenticación en UI.
