import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        applicationId = AndroidConfig.APPLICATION_ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isShrinkResources = BuildTypeDebug.isShrinkingEnabled
        }

        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isShrinkResources = BuildTypeRelease.isShrinkingEnabled
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(LibraryDependency.KOTLIN)
    implementation(LibraryDependency.CORE_KTX)
    implementation(LibraryDependency.APP_COMPAT)
    implementation(LibraryDependency.MATERIAL)
    implementation(LibraryDependency.RETROFIT)
    implementation(LibraryDependency.RETROFIT_COROUTINES_ADAPTER)
    implementation(LibraryDependency.RETROFIT_GSON_CONVERTER)
    implementation(LibraryDependency.LOGGING_INTERCEPTOR)
    implementation(LibraryDependency.COROUTINES)
    implementation(LibraryDependency.COROUTINES_ANDROID)
}