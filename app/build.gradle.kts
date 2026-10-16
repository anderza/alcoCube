plugins {
    id("com.android.application")
}

android {
    namespace = "by.kubik.drunkdice"
    compileSdk = 34

    defaultConfig {
        applicationId = "by.kubik.drunkdice"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Игра лежит в web/ и используется и как сайт, и как содержимое приложения.
    sourceSets {
        getByName("main") {
            assets.srcDirs("../web")
        }
    }
}
