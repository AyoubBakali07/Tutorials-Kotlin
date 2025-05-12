plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.efm_mobile_v3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.efm_mobile_v3"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Utilise exclusivement le versionCatalog :
    implementation(libs.androidx.core.ktx)               // core-ktx:1.9.0
    implementation(libs.androidx.lifecycle.runtime.ktx)  // lifecycle-runtime-ktx:2.8.7
    implementation(libs.androidx.activity.compose)       // activity-compose:1.10.1

    implementation(platform(libs.androidx.compose.bom))  // BOM Compose
    implementation(libs.androidx.ui)                     // compose-ui
    implementation(libs.androidx.ui.graphics)            // compose-ui-graphics
    implementation(libs.androidx.ui.tooling.preview)    // compose-tooling-preview
    implementation(libs.androidx.material3)              // material3

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation( "androidx.activity:activity-compose:1.7.0")
    implementation ("androidx.compose.ui:ui:1.4.0")
    implementation ("androidx.compose.material:material:1.4.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
//    implementation("androidx.core:core-ktx:1.9.0")
}
