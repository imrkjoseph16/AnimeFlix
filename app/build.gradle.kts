plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.imrkjoseph.animenation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.imrkjoseph.animenation"
        minSdk = 24
        targetSdk = 34
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")

    // Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx2:1.7.1")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    implementation("androidx.media3:media3-exoplayer-hls:1.2.1")
    implementation("com.google.android.gms:play-services-cast:21.4.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.4.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.1.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Coil Image
    implementation("io.coil-kt:coil:2.4.0")

    // Gson Library
    implementation("com.google.code.gson:gson:2.10.1")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // Shimmer Loading
    implementation("io.supercharge:shimmerlayout:2.1.0")

    // Carousel Indicator
    implementation("com.tbuonomo:dotsindicator:5.0")

    // RxBinding
    implementation("com.jakewharton.rxbinding:rxbinding:0.4.0")

    // Firebase
    implementation("com.google.firebase:firebase-auth:22.3.1")
    implementation("com.google.firebase:firebase-firestore:24.10.1")
    implementation("com.google.firebase:firebase-storage:20.3.0")

    // Fancy toast
    implementation("io.github.shashank02051997:FancyToast:2.0.2")

    // Leak Canary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.8.1")

    // Exo Player
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("androidx.media3:media3-exoplayer:1.3.0-alpha01")
    implementation("androidx.media3:media3-ui:1.3.0-alpha01")

    // Motion Layout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}