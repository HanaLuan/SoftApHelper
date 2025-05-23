import java.io.BufferedReader
import java.io.InputStreamReader

plugins {
    id("com.android.application")
}

android {

    defaultConfig {
        applicationId = "com.xhy.xp.softaphelper"
        minSdk = 23
        targetSdk = 33
        versionCode = getGitCommitCount().toIntOrNull()  ?: 1
        versionName = "SoftAPHelper" + ".r${getGitCommitCount()}" + "-Miao${getGitCommitHash()}"
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

    namespace = "com.xhy.xp.softaphelper"
}

dependencies {
    compileOnly(files("libs/api-82.jar"))
}

fun getGitCommitHash(): String {
    val builder = ProcessBuilder("git", "rev-parse", "--short", "HEAD")
    val process = builder.start()
    val reader = process.inputReader()
    val hash = reader.readText().trim()
    return if (hash.isNotEmpty()) ".$hash" else ""
}

fun getGitCommitCount(): String {
    val process = Runtime.getRuntime().exec("git rev-list --count HEAD")
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val commitCount = reader.readLine()?.toIntOrNull() ?: 0
    reader.close()
    process.waitFor()
    return (commitCount + 1).toString()
}
