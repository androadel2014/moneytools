package com.andrewmetry.moneytools
import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
class AdManager(private val context: Context, private val gatekeeper: AdGatekeeper) {
 private var interstitial: InterstitialAd? = null
 fun loadInterstitial() {
  if (gatekeeper.isRemoveAdsOwned()) return
  InterstitialAd.load(context, BuildConfig.ADMOB_INTERSTITIAL_ID, AdRequest.Builder().build(), object : InterstitialAdLoadCallback() { override fun onAdLoaded(ad: InterstitialAd) { interstitial = ad } override fun onAdFailedToLoad(e: LoadAdError) { interstitial = null } })
 }
 fun maybeShowInterstitial(activity: Activity) {
  val now = System.currentTimeMillis()
  if (!gatekeeper.shouldShowInterstitial(now)) return
  val ad = interstitial ?: return
  gatekeeper.recordInterstitialShown(now)
  ad.show(activity)
  loadInterstitial()
 }
}
