import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use { load(it) }
    }
}

fun localProperty(key: String, defaultValue: String = ""): String {
    return localProperties.getProperty(key)?.trim().orEmpty().ifEmpty { defaultValue }
}

android {
    namespace = "com.mislugares"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mislugares"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "GOOGLE_PHOTOS_BASE_URL", "\"https://photoslibrary.googleapis.com/\"")
        buildConfigField("String", "GOOGLE_PHOTOS_READ_SCOPE", "\"https://www.googleapis.com/auth/photoslibrary.readonly\"")
        buildConfigField("String", "PLACES_API_KEY", "\"${localProperty("PLACES_API_KEY")}\"")

        manifestPlaceholders["PLACES_API_KEY"] = localProperty("PLACES_API_KEY")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp.logging)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.analytics.ktx)

    implementation(libs.play.services.auth)
    implementation(libs.places)
}
