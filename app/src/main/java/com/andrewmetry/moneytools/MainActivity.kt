package com.andrewmetry.moneytools
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
sealed class Screen { object Home : Screen(); object Loan : Screen(); object Tip : Screen(); object Discount : Screen(); object Savings : Screen(); object Settings : Screen() }
class MainActivity : ComponentActivity() {
 private lateinit var gatekeeper: AdGatekeeper
 private lateinit var billing: BillingManager
 private lateinit var adManager: AdManager
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  gatekeeper = AdGatekeeper(this)
  billing = BillingManager(this, gatekeeper)
  adManager = AdManager(this, gatekeeper)
  ConsentManager(this).requestConsent { MobileAds.initialize(this) { adManager.loadInterstitial() } }
  billing.start()
  setContent { MoneyToolsTheme { var screen by remember { mutableStateOf<Screen>(Screen.Home) }; var removeAdsOwned by remember { mutableStateOf(gatekeeper.isRemoveAdsOwned()) }; var priceText by remember { mutableStateOf(billing.removeAdsPriceText()) }; billing.onOwnedChanged = { removeAdsOwned = it }; billing.onPriceLoaded = { priceText = it }; billing.onMessage = { _ -> }; val onCalculated: () -> Unit = { if (gatekeeper.onCalculationCompleted()) adManager.maybeShowInterstitial(this) }; val banner: @Composable () -> Unit = { if (!removeAdsOwned) { AndroidView(factory = { ctx -> AdView(ctx).apply { setAdSize(AdSize.BANNER); adUnitId = BuildConfig.ADMOB_BANNER_ID; loadAd(AdRequest.Builder().build()) } }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) } }; BackHandler(enabled = screen != Screen.Home) { screen = Screen.Home }; when (screen) { is Screen.Home -> HomeScreen(onTool = { screen = it }, onSettings = { screen = Screen.Settings }, banner = banner); is Screen.Loan -> LoanScreen(onCalculated, banner); is Screen.Tip -> TipScreen(onCalculated, banner); is Screen.Discount -> DiscountScreen(onCalculated, banner); is Screen.Savings -> SavingsScreen(onCalculated, banner); is Screen.Settings -> SettingsScreen(removeAdsOwned = removeAdsOwned, priceText = priceText, onRemoveAds = { billing.launchRemoveAds(this) }, onRestore = { billing.restorePurchases() }, onSupport = { val i = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:androadel2014@gmail.com")); i.putExtra(Intent.EXTRA_SUBJECT, "MoneyTools feedback"); startActivity(Intent.createChooser(i, "Support & Feedback")) }) } } }
 }
}
@Composable fun HomeScreen(onTool: (Screen) -> Unit, onSettings: () -> Unit, banner: @Composable () -> Unit) { Column(Modifier.background(Navy).fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) { Text("MoneyTools", fontSize = 40.sp, fontWeight = FontWeight.Bold, color = WhiteText); Text("Loan, Tip & Tax Calculator", fontSize = 18.sp, color = MoneyGold, modifier = Modifier.padding(bottom = 16.dp)); ToolCard("Loan Calculator", "Monthly payment, total interest and total cost.", MoneyGreen) { onTool(Screen.Loan) }; ToolCard("Tip & Split", "Tip, total and what each person pays.", MoneyGold) { onTool(Screen.Tip) }; ToolCard("Discount & Tax", "Discount first, then tax on the discounted price.", MoneyGreen) { onTool(Screen.Discount) }; ToolCard("Savings Growth", "Monthly compounding with a monthly deposit.", MoneyGold) { onTool(Screen.Savings) }; Spacer(Modifier.height(12.dp)); GreenButton("Settings") { onSettings() }; banner() } }
@Composable fun SettingsScreen(removeAdsOwned: Boolean, priceText: String, onRemoveAds: () -> Unit, onRestore: () -> Unit, onSupport: () -> Unit) { Column(Modifier.background(Navy).fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) { Text("Settings", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = WhiteText); Text("Our ad promise: MoneyTools shows one small banner ad at the bottom of the Home screen and below results, and sometimes one full-screen ad after you finish a calculation. We never show ads while you type. We never show more than one full-screen ad in 3 minutes. We never show rewarded ads. Buying Remove Ads once removes every ad, forever.", fontSize = 17.sp, color = SoftText, modifier = Modifier.padding(vertical = 16.dp)); if (removeAdsOwned) { Text("Remove Ads is on. Thank you — this app is now ad-free.", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MoneyGreen, modifier = Modifier.padding(bottom = 12.dp)) } else { GreenButton("Remove Ads — " + priceText) { onRemoveAds() } }; GreenButton("Restore Purchases") { onRestore() }; GreenButton("Support & Feedback") { onSupport() }; Text("MoneyTools works fully offline. No account, no sign-in, no analytics. Nothing is stored except Remove Ads ownership, the last full-screen ad time, and how many calculations you have finished.", fontSize = 15.sp, color = SoftText, modifier = Modifier.padding(top = 12.dp)) } }
