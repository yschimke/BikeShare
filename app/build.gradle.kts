apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.3"
    defaultConfig {
        applicationId "com.surrus.bikeshare"
        minSdkVersion 21
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    composeOptions {
        kotlinCompilerVersion "1.4.0-rc"
        kotlinCompilerExtensionVersion = "${Versions.compose}"
    }

    buildFeatures {
        compose true
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude 'META-INF/*.kotlin_module'
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += ["-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check", "-Xskip-metadata-version-check"]
    }
}

dependencies {
    implementation "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.3.1'
    implementation 'com.google.android.material:material:1.1.0'

    implementation Compose.ui
    implementation Compose.uiGraphics
    implementation Compose.uiTooling
    implementation Compose.foundationLayout
    implementation Compose.material
    implementation Compose.runtimeLiveData

    implementation Koin.core
    implementation Koin.android
    implementation Koin.androidViewModel


    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.3.0'
    implementation 'androidx.navigation:navigation-ui-ktx:2.3.0'

    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"

    testImplementation 'junit:junit:4.13'
    androidTestImplementation 'androidx.test:runner:1.2.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

    implementation project(":common")
}
