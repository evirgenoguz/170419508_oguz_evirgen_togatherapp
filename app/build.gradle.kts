import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.clappstertech.togatherandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.clappstertech.togatherandroid"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
}

dependencies {

    implementation(projects.core.presentation)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.core.domain)

    implementation(projects.presentation.splash)
    implementation(projects.presentation.sample)
    implementation(projects.presentation.auth)
    implementation(projects.presentation.home)
    implementation(projects.presentation.foryou)
    implementation(projects.presentation.profile)
    implementation(projects.presentation.groupdetail)
    implementation(projects.location)

    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.firebase.crashlytics)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}