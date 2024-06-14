import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    alias(libs.plugins.compose.compiler)
}

group = "com.zzy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

composeCompiler {
    enableStrongSkippingMode = true
}

dependencies {
    // Navigator
    api(libs.voyager.navigator)
    // Screen Model
    api(libs.voyager.screenmodel)
    // BottomSheetNavigator
    api(libs.voyager.bottom.sheet.navigator)
    // TabNavigator
    api(libs.voyager.tab.navigator)
    // Transitions
    api(libs.voyager.transitions)
    implementation("org.apache.poi:poi:5.2.5")
    implementation("org.apache.poi:poi-ooxml:5.2.5")
    implementation("org.apache.poi:poi-scratchpad:5.2.5")

    implementation("org.apache.logging.log4j:log4j-core:2.23.1")
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)
    implementation(compose.desktop.currentOs)


}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "test"
            packageVersion = "1.0.0"
        }
    }
}
