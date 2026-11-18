package com.andrewmetry.moneytools
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable fun MoneyField(label: String, value: String, onChange: (String) -> Unit) { OutlinedTextField(value = value, onValueChange = onChange, label = { Text(label) }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), singleLine = true, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MoneyGreen, unfocusedBorderColor = SoftText, focusedLabelColor = MoneyGreen, unfocusedLabelColor = SoftText, focusedTextColor = WhiteText, unfocusedTextColor = WhiteText), modifier = Modifier.fillMaxWidth()) }
@Composable fun LabelSlider(label: String, value: Float, range: ClosedFloatingPointRange<Float>, onChange: (Float) -> Unit) { Text(label, fontSize = 16.sp, color = WhiteText); Slider(value = value, onValueChange = onChange, valueRange = range, colors = SliderDefaults.colors(thumbColor = MoneyGreen, activeTrackColor = MoneyGreen, inactiveTrackColor = SoftText)) }
@Composable fun LoanScreen(onCalculated: () -> Unit, banner: @Composable () -> Unit) {
 var amount by remember { mutableStateOf("10000") }
 var apr by remember { mutableStateOf("6") }
 var years by remember { mutableFloatStateOf(3f) }
 var monthly by remember { mutableStateOf(Calculators.money(Calculators.loanMonthlyPayment(10000.0, 6.0, 36))) }
 var interest by remember { mutableStateOf(Calculators.money(Calculators.loanTotalInterest(10000.0, 6.0, 36))) }
 var total by remember { mutableStateOf(Calculators.money(Calculators.loanTotalCost(10000.0, 6.0, 36))) }
 Column(Modifier.verticalScroll(rememberScrollState())) { ScreenShell("Loan Calculator") { MoneyField("Loan amount ($)", amount) { amount = it }; MoneyField("APR (%)", apr) { apr = it }; LabelSlider("Term: " + years.toInt() + " years", years, 1f..30f) { years = it }; Spacer(Modifier.height(8.dp)); GreenButton("Calculate") { val p = amount.toDoubleOrNull() ?: 10000.0; val a = apr.toDoubleOrNull() ?: 6.0; val n = years.toInt() * 12; monthly = Calculators.money(Calculators.loanMonthlyPayment(p, a, n)); interest = Calculators.money(Calculators.loanTotalInterest(p, a, n)); total = Calculators.money(Calculators.loanTotalCost(p, a, n)); onCalculated() }; ResultCard("Monthly payment", monthly, listOf("Total interest" to interest, "Total cost" to total)); banner() } }
}
@Composable fun TipScreen(onCalculated: () -> Unit, banner: @Composable () -> Unit) {
 var bill by remember { mutableStateOf("80") }
 var tipPct by remember { mutableFloatStateOf(18f) }
 var people by remember { mutableFloatStateOf(2f) }
 var perPerson by remember { mutableStateOf(Calculators.money(47.20)) }
 var tipAmt by remember { mutableStateOf(Calculators.money(14.40)) }
 var total by remember { mutableStateOf(Calculators.money(94.40)) }
 Column(Modifier.verticalScroll(rememberScrollState())) { ScreenShell("Tip & Split") { MoneyField("Bill amount ($)", bill) { bill = it }; LabelSlider("Tip: " + tipPct.toInt() + "%", tipPct, 0f..40f) { tipPct = it }; LabelSlider("People: " + people.toInt(), people, 1f..12f) { people = it }; Spacer(Modifier.height(8.dp)); GreenButton("Calculate") { val b = bill.toDoubleOrNull() ?: 80.0; tipAmt = Calculators.money(Calculators.tipAmount(b, tipPct.toDouble())); total = Calculators.money(Calculators.tipTotal(b, tipPct.toDouble())); perPerson = Calculators.money(Calculators.tipPerPerson(b, tipPct.toDouble(), people.toInt())); onCalculated() }; ResultCard("Each person pays", perPerson, listOf("Tip amount" to tipAmt, "Total with tip" to total)); banner() } }
}
