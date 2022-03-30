object AndroidConfig {
    const val COMPILE_SDK_VERSION = 30
    const val MIN_SDK_VERSION = 28
    const val TARGET_SDK_VERSION = 30
    const val BUILD_TOOLS_VERSION = "30.0.3"

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val APPLICATION_ID = "com.treelineinteractive.recruitmenttask"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

}

interface BuildType {
    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
    val isShrinkingEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
    override val isShrinkingEnabled = false
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = true
    override val isShrinkingEnabled = true
}