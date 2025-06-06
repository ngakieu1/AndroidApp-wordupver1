// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
buildscript {

    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository
        mavenCentral()  // Maven Central repository
    }

    dependencies {
        // Add the Maven coordinates and latest version of the plugin
        classpath ("com.google.gms:google-services:4.4.2")
        classpath ("com.google.firebase:firebase-appdistribution-gradle:5.1.1")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:3.0.3")
        classpath ("com.google.firebase:perf-plugin:1.4.2")

    }
}

