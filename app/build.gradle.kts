plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.10"
}

android {
    namespace = "com.example.ete"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ete"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("releaseConfigs") {
            storeFile = file("../ete.jks")
            storePassword = "ete_123"
            keyAlias = "key0"
            keyPassword = "ete_123"
        }
        create("debugConfigs") {
            storeFile = file("../ete.jks")
            storePassword = "ete_123"
            keyAlias = "key0"
            keyPassword = "ete_123"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("boolean", "EnableAnim", "true")

            //Staging
            buildConfigField("String", "BASE_URL", "\"https://stage-api.ete.space/api/\"")
            buildConfigField("String", "API_KEY", "\"8edf1c68-57f2-4ef8-a492-1768c5c216b3\"")
            buildConfigField("String", "PLACE_API_KEY", "\"QUl6YVN5Q3hrdlNtLXVuZ1p0WHdiNE9fZjh6VEZ6MUZ1eUFJMzNj\"")
            buildConfigField("String", "AWS_ENVIRONMENT", "\"staging\"")

            signingConfig = signingConfigs.getByName("releaseConfigs")
        }

        debug {
            buildConfigField("boolean", "EnableAnim", "true")

            //Staging
            buildConfigField("String", "BASE_URL", "\"https://stage-api.ete.space/api/\"")
            buildConfigField("String", "API_KEY", "\"8edf1c68-57f2-4ef8-a492-1768c5c216b3\"")
            buildConfigField("String", "PLACE_API_KEY", "\"QUl6YVN5Q3hrdlNtLXVuZ1p0WHdiNE9fZjh6VEZ6MUZ1eUFJMzNj\"")
            buildConfigField("String", "AWS_ENVIRONMENT", "\"staging\"")

            signingConfig = signingConfigs.getByName("debugConfigs")
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.gson)

    //Coroutines
    implementation(libs.jakewharton.retrofit2.kotlin.coroutines.adapter)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //RxJava
    implementation(libs.io.reactivex.rxjava2.rxjava3)
    implementation(libs.io.reactivex.rxjava2.rxandroid3)

    // Hilt
    implementation (libs.dagger)
    kapt (libs.dagger.compiler)
    // For Kotlin
    kapt (libs.google.dagger.compiler)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation (libs.accompanist.systemuicontroller)
}