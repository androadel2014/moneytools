package com.andrewmetry.moneytools
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
class AdRulesTest {
 private val now = 1_000_000_000L
 @Test fun deniedWhenRemoveAdsOwned() { assertFalse(AdRules.canShowInterstitial(true, 5, 0L, now)) }
 @Test fun deniedOnFirstEverCalculation() { assertFalse(AdRules.canShowInterstitial(false, 1, 0L, now)); assertFalse(AdRules.canShowInterstitial(false, 0, 0L, now)) }
 @Test fun deniedInside180sWindow() { assertFalse(AdRules.canShowInterstitial(false, 3, now - 179_999L, now)); assertFalse(AdRules.canShowInterstitial(false, 3, now - 60_000L, now)) }
 @Test fun allowedAfter180sWindow() { assertTrue(AdRules.canShowInterstitial(false, 2, now - 180_001L, now)); assertTrue(AdRules.canShowInterstitial(false, 2, 0L, now)) }
}
