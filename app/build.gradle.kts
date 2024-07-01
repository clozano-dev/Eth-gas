import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.clozanodev.ethereumgastracker"
    compileSdk = 34

    val properties = Properties()
    val localPropertiesFile = project.rootProject.file("local.properties")
    localPropertiesFile.inputStream().use { properties.load(it) }

    defaultConfig {
        applicationId = "com.clozanodev.ethereumgastracker"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        android.buildFeatures.buildConfig = true

        buildConfigField("String", "API_KEY", "\"${properties.getProperty("API.KEY")}\"")

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.ui.tooling)
    implementation(libs.androidx.material)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation (libs.androidx.navigation.compose)

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation (libs.androidx.work.runtime)
    implementation (libs.androidx.hilt.navigation.compose)
    implementation (libs.hilt.android)
    implementation(libs.androidx.runtime.livedata)
    kapt (libs.hilt.android.compiler)

    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}