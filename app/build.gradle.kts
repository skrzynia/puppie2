plugins {
    id("org.jetbrains.kotlin.android")
    id("com.android.application")
    id("com.google.gms.google-services")
    id("io.realm.kotlin") version "1.13.0"
    id ("org.jetbrains.kotlin.plugin.noarg") version "1.9.22"
}

android {
    namespace = "org.wit.puppie2"
    compileSdk = 34

    noArg {
        annotation("org.wit.puppie2.dao.Person")
        annotation("org.wit.puppie2.dao.Place")
        invokeInitializers = true
    }


    defaultConfig {
        applicationId = "org.wit.puppie2"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
//            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

//    configurations.all{
//        resolutionStrategy {
//            exclude("org.mongodb")
//        }
//    }

    packaging {
        resources.pickFirsts.add("META-INF/native-image/org.mongodb/bson/native-image.properties")
    }
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    buildToolsVersion = "34.0.0"
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2023.10.01")
    implementation(composeBom)
    implementation("io.realm.kotlin:library-base:1.11.0")
    implementation("io.realm.kotlin:library-sync:1.11.0")
    implementation("io.realm:realm-gradle-plugin:10.15.1")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation ("com.firebaseui:firebase-ui-storage:7.2.0")
    implementation("org.mongodb:bson:4.11.0")
    implementation ("com.firebaseui:firebase-ui-storage:7.2.0")
    implementation ("androidx.annotation:annotation:1.7.1")
    androidTestImplementation(composeBom)
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.compose.material:material-icons-core")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("com.google.firebase:firebase-storage")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.compose.runtime:runtime-rxjava2")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.1")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-firestore")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("org.slf4j:slf4j-simple:1.7.9")
    implementation("org.mongodb:mongodb-driver-kotlin-sync:4.11.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("com.google.firebase:firebase-auth")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.jakewharton.timber:timber:5.0.1")
    implementation("com.github.ajalt:timberkt:1.5.1")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.moshi:moshi-kotlin:1.13.0")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.core:core-splashscreen:1.1.0-alpha02")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}