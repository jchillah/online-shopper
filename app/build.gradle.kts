plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.syntax_institut.jetpack.a04_05_online_shopper"
    compileSdk = 35

    defaultConfig {
        applicationId = "de.syntax_institut.jetpack.a04_05_online_shopper"
        minSdk = 27
        //noinspection OldTargetApi
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // HIER kommt die Signierungskonfiguration
    signingConfigs {
        create("release") {
            storeFile = file("/Users/jchillah/dev/Kotlin-Keytool/keystore.jks")
            storePassword = "17223326"
            keyAlias = "your_alias"
            keyPassword = "17223326"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Für ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose.v241)
    implementation(libs.androidx.material)

    // Retrofit + Moshi
    implementation(libs.retrofit.v290)
    implementation(libs.converter.moshi.v290)
    implementation(libs.squareup.moshi)

    // Für Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Für AsyncImage
    implementation(libs.coil.compose.v250)

    // Logging interceptor
    implementation(libs.logging.interceptor)

    implementation(libs.converter.gson)
    implementation(libs.squareup.moshi)
    implementation(libs.moshi)
    implementation(libs.androidx.core.ktx.v1100)
    implementation(libs.androidx.activity.compose.v170)
    implementation(libs.material3)
    implementation(libs.androidx.ui.v131)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.foundation)
}
