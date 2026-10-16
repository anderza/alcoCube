plugins {
    id("com.android.application")
}

// Версия и подпись задаются снаружи (GitHub Actions или ваш терминал), чтобы в репозитории не было секретов.
val appVersionCode = (System.getenv("VERSION_CODE") ?: "1").toInt()
val appVersionName = System.getenv("VERSION_NAME") ?: "1.0"

val keystoreFile = System.getenv("KEYSTORE_FILE")?.let { file(it) }
val hasKeystore = keystoreFile != null && keystoreFile.exists()

base.archivesName.set("alcoCube")

android {
    namespace = "by.kubik.drunkdice"
    compileSdk = 34

    defaultConfig {
        applicationId = "by.kubik.drunkdice"
        minSdk = 24
        targetSdk = 34
        versionCode = appVersionCode
        versionName = appVersionName
    }

    signingConfigs {
        if (hasKeystore) {
            create("release") {
                storeFile = keystoreFile
                storePassword = System.getenv("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            if (hasKeystore) {
                signingConfig = signingConfigs.getByName("release")
            }
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
