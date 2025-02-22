plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.newsapp.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "APIKEY", "\"CHANGE-WITH-YOUR-API-KEY\"")
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
    implementation(libs.bundles.core.impls)
    testImplementation(libs.core.testimpl.room.testing)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.core.kapt.androidx.room.compiler)

    //main
    implementation(libs.bundles.main.impls)
    testImplementation(libs.main.testimpl.junit)
    androidTestImplementation(libs.bundles.main.androidtestimpls)
    kapt(libs.bundles.main.kapts)
}