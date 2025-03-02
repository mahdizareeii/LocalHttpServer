plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.android.localserver"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    packagingOptions(action = {
        exclude("META-INF/*")
        // Due to https://github.com/Kotlin/kotlinx.coroutines/issues/2023
        exclude("META-INF/licenses/*")
        exclude("**/attach_hotspot_windows.dll")
    })
}

dependencies {
    implementation("io.ktor:ktor-server-core:1.6.2")
    implementation("io.ktor:ktor-server-netty:1.6.2")
    implementation("io.ktor:ktor-gson:1.2.5")
}