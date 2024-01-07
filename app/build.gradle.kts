import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 34

    namespace = "com.yifeplayte.maxmipadinput"

    defaultConfig {
        applicationId = "com.yifeplayte.maxmipadinput"
        minSdk = 31
        targetSdk = 34
        versionCode = 14
        versionName = "1.3.5"

        applicationVariants.configureEach {
            outputs.configureEach {
                if (this is BaseVariantOutputImpl) {
                    outputFileName = outputFileName.replace("app", rootProject.name).replace(Regex("debug|release"), versionName)
                }
            }
        }
    }

    buildTypes {
        named("release") {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles("proguard-rules.pro")
        }
        named("debug") {
            versionNameSuffix = "-debug-" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now())
        }
    }

    androidResources {
        additionalParameters += "--allow-reserved-package-id"
        additionalParameters += "--package-id"
        additionalParameters += "0x45"
        generateLocaleConfig = true
    }

    packaging.resources {
            excludes += "/META-INF/**"
            excludes += "/kotlin/**"
            excludes += "/*.txt"
            excludes += "/*.bin"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(project(":blockmiui"))
    implementation("com.github.kyuubiran:EzXHelper:2.0.8")
    implementation("io.github.ranlee1:jpinyin:1.0.1")
    implementation("org.luckypray:DexKit:1.1.8")
    compileOnly("de.robv.android.xposed:api:82")
}
