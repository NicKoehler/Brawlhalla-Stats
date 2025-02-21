import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aboutlibraries)
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.nickoehler.brawlhalla"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nickoehler.brawlhalla"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "0.0.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    signingConfigs {
        create("release") {
            storeFile = File(projectDir, "keystore.jks")
            storePassword = properties.getProperty("KEYSTORE_PASSWORD")
            keyAlias = properties.getProperty("KEYSTORE_ALIAS")
            keyPassword = properties.getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {

        debug {
            buildConfigField("String", "API_HOST", "\"api.brawlhalla.com\"")
            buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")
        }

        release {
            buildConfigField("String", "API_HOST", "\"api.brawlhalla.com\"")
            buildConfigField("String", "API_KEY", "\"${properties.getProperty("API_KEY")}\"")

            signingConfig = signingConfigs.getByName("release")

            isMinifyEnabled = true
            isShrinkResources = true

            ndk {
                debugSymbolLevel = "FULL"
            }

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
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.adaptive.layout.android)
    implementation(libs.androidx.adaptive.navigation.android)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.jetbrains.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.material.icons.extended)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)
    implementation(libs.coil.network.okhttp)

    // koin
    implementation(libs.koin.compose)

    // adaptive navigation
    implementation(libs.androidx.material3.adaptive.navigation.suite)

    // ktor
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    
    // splashscreen
    implementation(libs.androidx.core.splashscreen)

    // room
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // licenses
    implementation(libs.aboutlibraries.compose.m3)

    // widget
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)

}
