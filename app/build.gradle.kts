plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_KAPT)
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
        dataBinding = true
    }

    packagingOptions {
        exclude ("META-INF/*")
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
    implementation(LibraryDependency.CARD_VIEW)
    implementation(LibraryDependency.VIEW_MODEL_SCOPE)
    implementation(LibraryDependency.JUNIT)
    implementation(LibraryDependency.JUNIT_API)
    implementation(LibraryDependency.JUNIT_ENGINE)
    implementation(LibraryDependency.MOCKK)
    implementation(LibraryDependency.DAGGER)
    implementation(LibraryDependency.DAGGER_ANDROID)
    implementation(LibraryDependency.DAGGER_ANDROID_SUPPORT)
    implementation(LibraryDependency.FEST_ASSERT_CORE)
    testImplementation(LibraryDependency.JUNIT)

    kapt(LibraryDependency.DAGGER_COMPILER)
    kapt(LibraryDependency.DAGGER_PROCESSOR)
}