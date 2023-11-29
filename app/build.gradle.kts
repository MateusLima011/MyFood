@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kspAndroid)
}

android {
    namespace = "com.example.myfood"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myfood"
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
    buildFeatures {
        compose = true
        viewBinding = true
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

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.facebookShimmer)
    implementation(platform(libs.compose.bom))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(platform(libs.compose.bom))
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    //material design
    implementation(libs.google.material)

    //circle image view
    implementation(libs.circle.image)

    //scalable unit size
    implementation(libs.scalable.unit)

    //room database
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.rounded.image)
    implementation(libs.recyclerview)

    implementation(libs.devtools.ksp)

    //okHttp3
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.interceptor)

    //easy permission
    implementation(libs.easy.permission)

    //coroutines core
    implementation(libs.coroutines.core)

    //retrofit
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.gson)
    implementation(libs.retrofit2.serialization)

    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    api(libs.kotlinx.serialization)

}