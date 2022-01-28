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
        const val Gradle = "7.1.0"
        const val GoogleService = "4.3.3"
    }

    object Ktx {
        const val Core = "1.7.0"
        const val LifeCycle = "2.4.0"
    }

    object Compose {
        const val Lottie = "4.2.2"
        const val Activity = "1.4.0"
        const val Insets = "0.24.0-alpha"
        const val Master = "1.2.0-alpha01"
        const val Material = "1.2.0-alpha01"
        const val ConstraintLayout = "1.0.0"
        const val LifecycleViewModel = "2.4.0"
    }

    object Ui {
        const val Browser = "1.3.0"
        const val Material = "1.5.0"
        const val Splash = "1.0.0-beta01"
    }

    object Network {
        const val Jsoup = "1.14.3"
        const val OkHttp = "4.9.3"
        const val Retrofit = "2.9.0"
    }

    object Util {
        const val Erratum = "1.0.1"
        const val Logeukes = "1.0.1"
        const val Jackson = "2.13.1"
        const val LeakCanary = "2.8.1"
        const val FirebaseBom = "29.0.3"
        const val CheckDependencyUpdates = "1.5.0"
    }

    object Jetpack {
        const val Hilt = "2.40.5"
    }

    object OssLicense {
        const val Master = "17.0.0"
        const val Classpath = "0.10.4"
    }

    object Test {
        const val Arch = "1.1.1"
        const val JUnit = "4.13.2"
        const val Mockk = "1.12.2"
        const val MockWebServer = "4.9.3"
    }
}

object Dependencies {
    const val Hilt = "com.google.dagger:hilt-android:${Versions.Jetpack.Hilt}"
    const val FirebaseBom = "com.google.firebase:firebase-bom:${Versions.Util.FirebaseBom}"

    object Compiler {
        const val Hilt = "com.google.dagger:hilt-android-compiler:${Versions.Jetpack.Hilt}"
    }

    val Firebase = listOf("com.google.firebase:firebase-firestore-ktx")

    val Essential =
        listOf("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}")

    val Ktx = listOf(
        "androidx.core:core-ktx:${Versions.Ktx.Core}",
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Ktx.LifeCycle}",
    )

    val Compose = listOf(
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Master}",
        "com.airbnb.android:lottie-compose:${Versions.Compose.Lottie}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.compose.material:material:${Versions.Compose.Material}",
        "com.google.accompanist:accompanist-insets:${Versions.Compose.Insets}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.LifecycleViewModel}",
        "androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.ConstraintLayout}"
    )

    val Ui = listOf(
        "androidx.browser:browser:${Versions.Ui.Browser}",
        "androidx.core:core-splashscreen:${Versions.Ui.Splash}",
        "com.google.android.material:material:${Versions.Ui.Material}",
        "com.google.android.gms:play-services-oss-licenses:${Versions.OssLicense.Master}"
    )

    val Jackson = listOf(
        "com.fasterxml.jackson.core:jackson-core:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-databind:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-annotations:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.Util.Jackson}"
    )

    val Network = listOf(
        "org.jsoup:jsoup:${Versions.Network.Jsoup}",
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:converter-jackson:${Versions.Network.Retrofit}"
    )

    val Util = listOf(
        "io.github.jisungbin:erratum:${Versions.Util.Erratum}",
        "io.github.jisungbin:logeukes:${Versions.Util.Logeukes}",
    )

    val Debug = listOf(
        "com.squareup.leakcanary:leakcanary-android:${Versions.Util.LeakCanary}"
    )

    val Test = listOf(
        "junit:junit:${Versions.Test.JUnit}",
        "io.mockk:mockk:${Versions.Test.Mockk}",
        "android.arch.core:core-testing:${Versions.Test.Arch}",
        "com.squareup.okhttp3:mockwebserver:${Versions.Test.MockWebServer}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Essential.Coroutines}",
        "androidx.test:core:1.4.0",
        "org.mockito:mockito-core:4.3.1",
        "org.mockito.kotlin:mockito-kotlin:4.0.0"
    )
}
