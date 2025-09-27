pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://androidx.dev/snapshots/builds/14026374/artifacts/repository")
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://androidx.dev/snapshots/builds/14026374/artifacts/repository")
        }
    }
}
rootProject.name = "brawlhalla"
include(":app")
 