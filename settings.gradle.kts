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
        // Repositorio para librerías como Material-Calendar-View
        maven { 
            url = uri("https://jitpack.io")
            content {
                // Especificamos que este repositorio contiene los artefactos del grupo com.github.Applandeo
                includeGroup("com.github.Applandeo")
            }
        }
    }
}

rootProject.name = "AgendaPersonal"
include(":app")
