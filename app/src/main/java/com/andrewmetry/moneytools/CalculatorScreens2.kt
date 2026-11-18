package com.andrewmetry.moneytools
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
@Composable fun DiscountScreen(onCalculated: () -> Unit, banner: @Composable () -> Unit) {
 var price by remember { mutableStateOf("120") }
 var disc by remember { mutableFloatStateOf(25f) }
 var tax by remember { mutableStateOf("6") }
 var pay by remember { mutableStateOf(Calculators.money(95.40)) }
 var save by remember { mutableStateOf(Calculators.money(30.00)) }
 var taxAmt by remember { mutableStateOf(Calculators.money(5.40)) }
 Column(Modifier.verticalScroll(rememberScrollState())) { ScreenShell("Discount & Tax") { MoneyField("Price ($)", price) { price = it }; LabelSlider("Discount: " + disc.toInt() + "%", disc, 0f..90f) { disc = it }; MoneyField("Sales tax (%)", tax) { tax = it }; Spacer(Modifier.height(8.dp)); GreenButton("Calculate") { val p = price.toDoubleOrNull() ?: 120.0; val t = tax.toDoubleOrNull() ?: 6.0; save = Calculators.money(Calculators.discountAmount(p, disc.toDouble())); taxAmt = Calculators.money(Calculators.discountTax(p, disc.toDouble(), t)); pay = Calculators.money(Calculators.discountPay(p, disc.toDouble(), t)); onCalculated() }; ResultCard("You pay", pay, listOf("You save" to save, "Tax" to taxAmt)); Text("Discount is taken off first, then tax is charged on the discounted price.", fontSize = 14.sp, color = SoftText); banner() } }
}
@Composable fun SavingsScreen(onCalculated: () -> Unit, banner: @Composable () -> Unit) {
 var start by remember { mutableStateOf("1000") }
 var monthly by remember { mutableStateOf("200") }
 var apy by remember { mutableStateOf("5") }
 var years by remember { mutableFloatStateOf(10f) }
 val initial = Calculators.savingsGrowth(1000.0, 200.0, 5.0, 10)
 var finalAmt by remember { mutableStateOf(Calculators.money(initial.first)) }
 var interest by remember { mutableStateOf(Calculators.money(initial.second)) }
 Column(Modifier.verticalScroll(rememberScrollState())) { ScreenShell("Savings Growth") { MoneyField("Starting amount ($)", start) { start = it }; MoneyField("Monthly deposit ($)", monthly) { monthly = it }; MoneyField("APY (%)", apy) { apy = it }; LabelSlider("Years: " + years.toInt(), years, 1f..40f) { years = it }; Spacer(Modifier.height(8.dp)); GreenButton("Calculate") { val s = start.toDoubleOrNull() ?: 1000.0; val m = monthly.toDoubleOrNull() ?: 200.0; val a = apy.toDoubleOrNull() ?: 5.0; val r = Calculators.savingsGrowth(s, m, a, years.toInt()); finalAmt = Calculators.money(r.first); interest = Calculators.money(r.second); onCalculated() }; ResultCard("Final amount", finalAmt, listOf("Interest earned" to interest)); Text("Interest compounds monthly. Each monthly deposit is added after that month's interest.", fontSize = 14.sp, color = SoftText); banner() } }
}
