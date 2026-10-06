plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.sharechat.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sharechat.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 412
        versionName = "2026.40.0"

        buildConfigField("String", "FEED_BASE_URL", "\"https://feed.sharechat.example/\"")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.09.02"))
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Feed API client
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    testImplementation("junit:junit:4.13.2")
}
