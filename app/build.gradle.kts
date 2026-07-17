plugins {
    // These aliases MUST match the names in your libs.versions.toml [plugins] section
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    // Change this if your project folder is named differently
    namespace = "com.example.blood_donorapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.blood_donorapp"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    // Required even for Java projects in modern Android Studio
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // These implementation lines use the aliases from [libraries] in your TOML file
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)

    // FIREBASE DATABASE (Direct implementation)
    implementation("com.google.firebase:firebase-database:20.3.1")

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}