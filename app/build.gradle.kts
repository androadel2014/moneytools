import java.util.Properties
plugins { id("com.android.application"); id("org.jetbrains.kotlin.android"); id("org.jetbrains.kotlin.plugin.compose") }
val admobProps = Properties()
rootProject.file("admob.properties").inputStream().use { admobProps.load(it) }
val admobAppId = admobProps.getProperty("admob.app.id", "ca-app-pub-3940256099942544~3347511713")
val admobBannerId = admobProps.getProperty("admob.banner.id", "ca-app-pub-3940256099942544/6300978111")
val admobInterstitialId = admobProps.getProperty("admob.interstitial.id", "ca-app-pub-3940256099942544/1033173712")
val hasSigning = listOf("KEYSTORE_FILE", "KEYSTORE_PASSWORD", "KEY_ALIAS", "KEY_PASSWORD").all { System.getenv(it).isNullOrBlank().not() }
android {
 namespace = "com.andrewmetry.moneytools"
 compileSdk = 36
 defaultConfig { applicationId = "com.andrewmetry.moneytools"; minSdk = 26; targetSdk = 36; versionCode = 1; versionName = "1.0.0"; manifestPlaceholders["admobAppId"] = admobAppId; buildConfigField("String", "ADMOB_BANNER_ID", """ + admobBannerId + """); buildConfigField("String", "ADMOB_INTERSTITIAL_ID", """ + admobInterstitialId + """) }
 signingConfigs { if (hasSigning) { create("release") { storeFile = file(System.getenv("KEYSTORE_FILE")); storePassword = System.getenv("KEYSTORE_PASSWORD"); keyAlias = System.getenv("KEY_ALIAS"); keyPassword = System.getenv("KEY_PASSWORD") } } }
 buildTypes { release { if (hasSigning) { signingConfig = signingConfigs.getByName("release") }; isMinifyEnabled = false; isShrinkResources = false; proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro") } }
 compileOptions { sourceCompatibility = JavaVersion.VERSION_11; targetCompatibility = JavaVersion.VERSION_11 }
 buildFeatures { buildConfig = true; compose = true }
}
kotlin { compilerOptions { jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11) } }
dependencies {
 implementation("androidx.core:core-ktx:1.17.0")
 implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.3")
 implementation("androidx.activity:activity-compose:1.11.0")
 implementation(platform("androidx.compose:compose-bom:2025.09.01"))
 implementation("androidx.compose.ui:ui")
 implementation("androidx.compose.ui:ui-graphics")
 implementation("androidx.compose.ui:ui-tooling-preview")
 implementation("androidx.compose.material3:material3")
 implementation("com.google.android.gms:play-services-ads:25.5.0")
 implementation("com.google.android.ump:user-messaging-platform:4.0.0")
 implementation("com.android.billingclient:billing-ktx:9.1.0")
 debugImplementation("androidx.compose.ui:ui-tooling")
 testImplementation("junit:junit:4.13.2")
}
