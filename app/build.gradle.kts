plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.10"
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
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

            //Live
            /*buildConfigField("String", "BASE_URL", "\"https://api.ete.space/api/\"")
            buildConfigField("String", "API_KEY", "\"8edf1c68-57f2-4ef8-a492-1768c5c216b3\"")
            buildConfigField("String", "PLACE_API_KEY", "\"QUl6YVN5Q3hrdlNtLXVuZ1p0WHdiNE9fZjh6VEZ6MUZ1eUFJMzNj\"")
            buildConfigField("String", "AWS_ENVIRONMENT", "\"production\"")*/

            //Staging
            buildConfigField("String", "BASE_URL", "\"https://stage-api.ete.space/api/\"")
            buildConfigField("String", "API_KEY", "\"8edf1c68-57f2-4ef8-a492-1768c5c216b3\"")
            buildConfigField("String", "PLACE_API_KEY", "\"QUl6YVN5Q3hrdlNtLXVuZ1p0WHdiNE9fZjh6VEZ6MUZ1eUFJMzNj\"")
            buildConfigField("String", "AWS_ENVIRONMENT", "\"staging\"")

            signingConfig = signingConfigs.getByName("releaseConfigs")
        }

        debug {
            buildConfigField("boolean", "EnableAnim", "true")

            //Live
            /*buildConfigField("String", "BASE_URL", "\"https://api.ete.space/api/\"")
            buildConfigField("String", "API_KEY", "\"8edf1c68-57f2-4ef8-a492-1768c5c216b3\"")
            buildConfigField("String", "PLACE_API_KEY", "\"QUl6YVN5Q3hrdlNtLXVuZ1p0WHdiNE9fZjh6VEZ6MUZ1eUFJMzNj\"")
            buildConfigField("String", "AWS_ENVIRONMENT", "\"production\"")*/

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
    implementation(libs.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.compose.material)
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

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.accompanist.systemuicontroller)

    //Cookie Bar
    implementation(libs.org.aviran.cookiebar2.cookiebar24)

    //Dagger Hit
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.coil.compose)

    //AWS
    implementation(libs.aws.android.sdk.s3)
    implementation(libs.amazonaws.aws.android.sdk.core)
    implementation(libs.aws.android.sdk.cognitoauth)
    implementation(libs.aws.android.sdk.cognitoidentityprovider)

    implementation(libs.timber)

    implementation(libs.google.play.services.auth.api.phone)
    implementation(libs.com.google.android.libraries.identity.googleid.googleid)
    implementation(libs.play.services.auth)

    //Google service
    implementation(libs.gms.play.services.location)
    implementation(libs.com.google.firebase.firebase.messaging.ktx2)
    implementation(libs.gms.play.services.auth)

    //Crash and Analytics
    implementation(libs.com.google.firebase.firebase.crashlytics.ktx2)
    implementation(libs.com.google.firebase.firebase.analytics.ktx2)
    implementation(libs.com.google.firebase.firebase.auth.ktx2)

    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.placeholder.material3)
    implementation(libs.androidx.foundation)
    implementation(libs.exoplayer)

    //Google place
    implementation(libs.com.google.android.libraries.places.places2)
}