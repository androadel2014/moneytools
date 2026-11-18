package com.andrewmetry.moneytools
import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
class BillingManager(context: Context, private val gatekeeper: AdGatekeeper) {
 private var productDetails: ProductDetails? = null
 var onOwnedChanged: ((Boolean) -> Unit)? = null
 var onPriceLoaded: ((String) -> Unit)? = null
 var onMessage: ((String) -> Unit)? = null
 private val purchasesListener = PurchasesUpdatedListener { result, purchases ->
  if (result.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) handlePurchases(purchases) else if (result.responseCode != BillingClient.BillingResponseCode.USER_CANCELED) onMessage?.invoke("Purchase did not complete. Please try again.")
 }
 private val client: BillingClient = BillingClient.newBuilder(context.applicationContext).setListener(purchasesListener).enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build()).build()
 fun start(onReady: () -> Unit = {}) { client.startConnection(object : BillingClientStateListener { override fun onBillingSetupFinished(r: BillingResult) { if (r.responseCode == BillingClient.BillingResponseCode.OK) { queryProductDetails(); restorePurchases(); onReady() } } override fun onBillingServiceDisconnected() {} }) }
 fun removeAdsPriceText(): String = productDetails?.oneTimePurchaseOfferDetails?.formattedPrice ?: "One-time purchase"
 private fun queryProductDetails() { val params = QueryProductDetailsParams.newBuilder().setProductList(listOf(QueryProductDetailsParams.Product.newBuilder().setProductId(AdRules.REMOVE_ADS_PRODUCT_ID).setProductType(BillingClient.ProductType.INAPP).build())).build(); client.queryProductDetailsAsync(params) { _, details -> productDetails = details.productDetailsList.firstOrNull(); productDetails?.let { onPriceLoaded?.invoke(it.oneTimePurchaseOfferDetails?.formattedPrice ?: "One-time purchase") } } }
 fun launchRemoveAds(activity: Activity) { val details = productDetails ?: run { onMessage?.invoke("Remove Ads is still loading. Please try again in a moment."); return }; val flow = BillingFlowParams.newBuilder().setProductDetailsParamsList(listOf(BillingFlowParams.ProductDetailsParams.newBuilder().setProductDetails(details).build())).build(); client.launchBillingFlow(activity, flow) }
 fun restorePurchases() { client.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP).build()) { _, purchases -> handlePurchases(purchases) } }
 private fun handlePurchases(purchases: List<Purchase>) { var owned = gatekeeper.isRemoveAdsOwned(); for (p in purchases) { if (p.products.contains(AdRules.REMOVE_ADS_PRODUCT_ID) && p.purchaseState == Purchase.PurchaseState.PURCHASED) { owned = true; if (!p.isAcknowledged) client.acknowledgePurchase(AcknowledgePurchaseParams.newBuilder().setPurchaseToken(p.purchaseToken).build()) { } } }; if (owned != gatekeeper.isRemoveAdsOwned()) gatekeeper.setRemoveAdsOwned(owned); onOwnedChanged?.invoke(owned) }
}
