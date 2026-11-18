# MoneyTools: Loan & Tip Calculator

Fast, offline, everyday money calculator suite for Android. Package: com.andrewmetry.moneytools. Version 1.0.0 (versionCode 1).

## Tools
- Loan Calculator: monthly payment, total interest, total cost. Loan amount, APR %, term 1-30 years.
- Tip & Split: tip amount, total with tip, each person pays. Bill, tip 0-40%, 1-12 people.
- Discount & Tax: discount first, then tax on the discounted price. You pay, you save, tax.
- Savings Growth: monthly compounding, deposit added each month after interest. Final amount and interest earned.

## Build
`./gradlew :app:assembleRelease :app:testDebugUnitTest`

Stack: Gradle 8.13, AGP 8.13.2, Kotlin 2.3.10, compileSdk/targetSdk 36, minSdk 26, Compose BOM 2025.09.01, activity-compose 1.11.0, Google Mobile Ads 25.5.0, UMP 4.0.0, Play Billing 9.1.0.

## Ads and Remove Ads
AdMob test IDs live in admob.properties (Google public test IDs). Gradle injects the app ID into the manifest and the ad unit IDs into BuildConfig. UMP consent is requested before any ad loads. All ad displays go through AdManager, which checks AdGatekeeper: no ads when Remove Ads is owned, no interstitial after the first-ever calculation, and at most one interstitial per 180 seconds (persisted). Banner ads appear only at the bottom of Home and below results, never while typing. No rewarded ads.

Remove Ads is a one-time non-consumable product (remove_ads) via Play Billing, with Restore Purchases. Ownership is cached in SharedPreferences for offline use. The price is shown in plain text in Settings before purchase.

## Signing
Release signs only when all four env vars exist: KEYSTORE_FILE, KEYSTORE_PASSWORD, KEY_ALIAS, KEY_PASSWORD. Without them, the release build is unsigned.

## Privacy
Offline forever. No account, no sign-in, no analytics. Only Remove Ads ownership, the interstitial timestamp, and a completed-calculations counter are stored (SharedPreferences). See PRIVACY.md.

## Support
Support & Feedback in Settings opens a mail to support@andrewmetry.com.
