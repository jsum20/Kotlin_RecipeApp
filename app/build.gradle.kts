plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.recipeapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.kotlinprojects.recipeapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    // Core
    implementation("androidx.core:core-ktx:1.17.0")

    // ---------------- Compose BOM ----------------
    implementation(platform("androidx.compose:compose-bom:2026.02.01"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2026.02.01"))

    // Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material-icons-extended")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.activity:activity-compose:1.12.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")

    // ---------------- Navigation ----------------
    implementation("androidx.navigation:navigation-compose:2.9.7")

    // ---------------- Hilt ----------------
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

    // ---------------- Paging ----------------
    implementation("androidx.paging:paging-compose:3.4.1")

    // ---------------- Networking ----------------
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Gson
    implementation("com.google.code.gson:gson:2.11.0")

    // ---------------- Images ----------------
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Kotlin reflection
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // ---------------- Unit Tests ----------------
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("com.google.truth:truth:1.4.2")

    // ---------------- Android Tests ----------------
    androidTestImplementation("io.mockk:mockk-android:1.13.10")

    androidTestImplementation("androidx.test:core:1.7.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test:rules:1.7.0")

    // Hilt testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.52")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.52")

    // Navigation testing
    androidTestImplementation("androidx.navigation:navigation-testing:2.9.7")

    // Compose testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}