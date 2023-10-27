// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kspAndroid) apply false
}
buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    }
    repositories {
        google()
        mavenCentral()
    }
}

allprojects{
    repositories{
        google()
        mavenCentral()
    }
}

true // Needed to make the Suppress annotation work for the plugins block


