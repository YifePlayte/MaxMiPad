plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.yifeplayte.maxmipadinput"
        minSdk = 31
        targetSdk = 33
        versionCode = 7
        versionName = "1.2.0"
    }

    buildTypes {
        named("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
        named("debug") {
            versionNameSuffix = "-debug-" + org.gradle.internal.time.Time.currentTimeMillis()
        }
    }

    androidResources {
        additionalParameters("--allow-reserved-package-id", "--package-id", "0x45")
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/**"
            excludes += "/kotlin/**"
            excludes += "/*.txt"
            excludes += "/*.bin"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    namespace = "com.yifeplayte.maxmipadinput"
}

dependencies {
    implementation(project(":blockmiui"))
    implementation("com.github.kyuubiran:EzXHelper:1.0.3")
    implementation("io.github.ranlee1:jpinyin:1.0.1")
    compileOnly("de.robv.android.xposed:api:82")
}
