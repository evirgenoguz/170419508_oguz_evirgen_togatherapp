import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.evirgenoguz.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    val localHost = gradleLocalProperties(rootDir, providers).getProperty("LOCAL_HOST")
    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"${localHost}api/\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"${localHost}api/\"")
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
    }
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.storage)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.retrofit)
    implementation(libs.gson.converter)
    implementation(libs.okhttp.logging.interceptor)

    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)

    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)

    implementation(libs.firebase.analytics)

}