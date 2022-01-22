import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 24
    const val targetSdk = 31
    const val compileSdk = 31
    const val jvmTarget = "11"
    const val versionCode = 1
    const val versionName = "1.0.0"

    val targetCompat = JavaVersion.VERSION_11
    val sourceCompat = JavaVersion.VERSION_11
}

object Versions {
    object Essential {
        const val Kotlin = "1.6.10"
        const val Coroutines = "1.6.0"
        const val Gradle = "7.1.0-rc01"
        const val Ktx = "1.7.0"
    }

    object Compose {
        const val Master = "1.2.0-alpha01"
        const val Material = "1.0.0-alpha03"
        const val Activity = "1.4.0"
        const val Insets = "0.24.0-alpha"
    }

    object Ui {
        const val Material = "1.5.0"
    }
}

object Dependencies {
    val Essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.Ktx}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"
    )

    val Compose = listOf(
        "androidx.compose.ui:ui:${Versions.Compose.Master}",
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Master}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.compose.material3:material3:${Versions.Compose.Material}",
        "com.google.accompanist:accompanist-insets:${Versions.Compose.Insets}"
    )

    val Ui = listOf(
        "com.google.android.material:material:${Versions.Ui.Material}"
    )
}
