plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id("io.realm.kotlin")
    id("com.google.devtools.ksp")
    id("com.rickclephas.kmp.nativecoroutines")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}

android {
    compileSdk = AndroidSdk.compile
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    namespace = "dev.johnoreilly.bikeshare.common"
}


// CocoaPods requires the podspec to have a version.
version = "1.0"

kotlin {
    androidTarget()
    jvm()

    listOf(
        iosArm64(), iosX64(), iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "common"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kotlinx.serialization)

            api(libs.koin.core)
            implementation(libs.koin.test)

            implementation(libs.bundles.ktor.common)
            implementation(libs.realm)
            api(libs.kmmViewModel)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(Deps.androidXLifecycleViewModel)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }

        jvmMain.dependencies {
            // hack to allow use of MainScope() in shared code used by JVM console app
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.kotlinCoroutines}")

            implementation(libs.ktor.client.java)
            implementation(libs.slf4j)
        }
    }
}

kotlin {
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations.get("main").kotlinOptions.freeCompilerArgs += "-Xexport-kdoc"
    }
}

multiplatformSwiftPackage {
    packageName("BikeShareKit")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
        macOS{ v("10_15") }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "17"
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}

