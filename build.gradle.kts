plugins {
    kotlin("android") version "2.1.0" apply false
    id("com.android.application") version "8.9.2" apply false
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
}