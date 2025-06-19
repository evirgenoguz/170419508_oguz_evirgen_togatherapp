pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ToGatherAndroid"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":data")
include(":domain")
include(":presentation:sample")
include(":core")
include(":presentation:auth")
include(":core:data")
include(":core:presentation")
include(":core:domain")
include(":presentation:splash")
include(":presentation:home")
include(":presentation:foryou")
include(":presentation:profile")
include(":location")
include(":presentation:groupdetail")
