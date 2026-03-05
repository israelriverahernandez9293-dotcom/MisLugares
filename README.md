# MisLugares Android Setup

Configuración base lista para abrir en Android Studio con:
- Firebase Authentication (correo/contraseña).
- Firebase Firestore.
- Google Places SDK (Android).
- Google Photos Library API (REST con Retrofit).

## 1) Archivos y llaves que debes agregar manualmente

1. Descarga `google-services.json` desde Firebase Console.
2. Copia el archivo en:
   ```
   app/google-services.json
   ```
3. Crea `local.properties` a partir de `local.properties.example` y agrega:
   ```properties
   sdk.dir=/ruta/a/android/sdk
   PLACES_API_KEY=AIzaSy...tu_key...
   ```

> `google-services.json` y `local.properties` están ignorados por git para evitar exponer secretos.

## 2) Firebase (Auth + Firestore)

En Firebase Console:
1. Ve a **Authentication > Sign-in method**.
2. Habilita **Email/Password**.
3. Ve a **Firestore Database** y crea la base en modo de prueba (o reglas seguras).

Código principal:
- `FirebaseAuthRepository`: login, registro, logout y estado de sesión.
- `UserProfileRepository`: lectura/escritura de perfil en colección `users`.
- `MisLugaresApp`: inicializa Firebase al arrancar.

## 3) Google Places SDK

1. Habilita **Places API** en Google Cloud.
2. Restringe la API key por app Android (package + SHA-1/SHA-256).
3. Define `PLACES_API_KEY` en `local.properties`.

`PlacesInitializer` inicializa el SDK una sola vez al arrancar la app.

## 4) Google Photos Library API

### 4.1 Configuración en Google Cloud
1. Habilita **Photos Library API**.
2. Configura pantalla de consentimiento OAuth.
3. Crea credenciales OAuth para Android.
4. Añade SHA-1/SHA-256 del keystore debug/release.

### 4.2 Scope recomendado
- `https://www.googleapis.com/auth/photoslibrary.readonly`

### 4.3 Flujo sugerido
1. Usuario inicia sesión en Firebase (email/password).
2. Solicita OAuth de Google solo cuando vaya a usar Photos.
3. Obtén token OAuth temporal.
4. Llama `GooglePhotosRepository.fetchAlbums(accessToken)`.
5. No guardes tokens en texto plano.

## 5) Estructura propuesta (mantenimiento)

```text
app/src/main/java/com/mislugares/
  auth/      -> login, registro, sesión (Firebase)
  firestore/ -> repositorios para Firestore
  photos/    -> OAuth/config y cliente Google Photos
  core/      -> inicialización global
  ui/        -> pantallas/viewmodels
```

## 6) Auto-correcciones aplicadas

- Se corrigió un bug en `buildConfigField` (string mal escapado que rompía compilación).
- Se agregó `MainActivity` launcher para que la app pueda iniciar correctamente.
- Se añadió inicialización segura de Places (si la key no existe, no crashea).
- Se movieron textos hardcodeados a `strings.xml`.

## 7) Checklist rápida

- [ ] `app/google-services.json` agregado localmente.
- [ ] `local.properties` creado con `PLACES_API_KEY`.
- [ ] Email/Password habilitado en Firebase.
- [ ] Firestore habilitado y reglas revisadas.
- [ ] Photos Library API habilitada en GCP.
- [ ] OAuth de Google Photos configurado con SHA.
- [ ] Manejo de errores de red/autenticación en UI.
