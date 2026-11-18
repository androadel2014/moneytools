package com.andrewmetry.moneytools
import android.app.Activity
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
class ConsentManager(private val activity: Activity) {
 private val consentInfo: ConsentInformation = UserMessagingPlatform.getConsentInformation(activity)
 fun requestConsent(onReady: () -> Unit) {
  val params = ConsentRequestParameters.Builder().build()
  consentInfo.requestConsentInfoUpdate(activity, params, { UserMessagingPlatform.loadAndShowConsentFormIfRequired(activity) { _ -> onReady() } }, { onReady() })
 }
 fun canRequestAds(): Boolean = consentInfo.canRequestAds()
}
