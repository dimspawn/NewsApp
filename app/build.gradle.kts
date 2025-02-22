plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.imaginatic.newsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.imaginatic.newsapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "APIKEY", "\"ed5de300b3534890b602c7381c752033\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    //core
    implementation(project(":core"))

    //main
    implementation(libs.bundles.main.impls)
    implementation(libs.activity)
    testImplementation(libs.main.testimpl.junit)
    androidTestImplementation(libs.bundles.main.androidtestimpls)
    kapt(libs.bundles.main.kapts)
}