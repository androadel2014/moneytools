package com.andrewmetry.moneytools
import android.content.Context
import android.content.SharedPreferences
object AdRules {
 const val INTERSTITIAL_MIN_INTERVAL_MS: Long = 180_000L
 const val REMOVE_ADS_PRODUCT_ID: String = "remove_ads"
 fun canShowInterstitial(removeAdsOwned: Boolean, completedCalculationsIncludingThis: Int, lastInterstitialShownAtMs: Long, nowMs: Long): Boolean {
  if (removeAdsOwned) return false
  if (completedCalculationsIncludingThis <= 1) return false
  if (lastInterstitialShownAtMs > 0L && nowMs - lastInterstitialShownAtMs < INTERSTITIAL_MIN_INTERVAL_MS) return false
  return true
 }
}
class AdGatekeeper(context: Context) {
 private val prefs: SharedPreferences = context.getSharedPreferences("moneytools_prefs", Context.MODE_PRIVATE)
 fun isRemoveAdsOwned(): Boolean = prefs.getBoolean("remove_ads_owned", false)
 fun setRemoveAdsOwned(owned: Boolean) { prefs.edit().putBoolean("remove_ads_owned", owned).apply() }
 fun completedCalculations(): Int = prefs.getInt("completed_calculations", 0)
 fun recordCompletedCalculation(): Int { val n = completedCalculations() + 1; prefs.edit().putInt("completed_calculations", n).apply(); return n }
 fun lastInterstitialShownAtMs(): Long = prefs.getLong("interstitial_last_shown_ms", 0L)
 fun recordInterstitialShown(nowMs: Long) { prefs.edit().putLong("interstitial_last_shown_ms", nowMs).apply() }
 fun shouldShowInterstitial(nowMs: Long = System.currentTimeMillis()): Boolean = AdRules.canShowInterstitial(isRemoveAdsOwned(), completedCalculations(), lastInterstitialShownAtMs(), nowMs)
 fun onCalculationCompleted(nowMs: Long = System.currentTimeMillis()): Boolean { val count = recordCompletedCalculation(); return AdRules.canShowInterstitial(isRemoveAdsOwned(), count, lastInterstitialShownAtMs(), nowMs) }
}
