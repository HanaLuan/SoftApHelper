pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        flatDir {
            dirs("app/libs")
        }
    }
}
//sourceControl {
//    gitRepository(uri("https://github.com/libxposed/api.git")) {
//        producesModule("")
//    }
//}
rootProject.name = "SoftApHelper"
include(":app")
