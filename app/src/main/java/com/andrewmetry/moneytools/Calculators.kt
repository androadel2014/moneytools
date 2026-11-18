package com.andrewmetry.moneytools
import kotlin.math.pow
object Calculators {
 fun loanMonthlyPayment(principal: Double, aprPercent: Double, months: Int): Double {
  if (months <= 0) return 0.0
  val r = aprPercent / 100.0 / 12.0
  if (r == 0.0) return principal / months
  val f = (1.0 + r).pow(months.toDouble())
  return principal * r * f / (f - 1.0)
 }
 fun loanTotalInterest(principal: Double, aprPercent: Double, months: Int): Double = loanMonthlyPayment(principal, aprPercent, months) * months - principal
 fun loanTotalCost(principal: Double, aprPercent: Double, months: Int): Double = loanMonthlyPayment(principal, aprPercent, months) * months
 fun tipAmount(bill: Double, tipPercent: Double): Double = bill * tipPercent / 100.0
 fun tipTotal(bill: Double, tipPercent: Double): Double = bill + tipAmount(bill, tipPercent)
 fun tipPerPerson(bill: Double, tipPercent: Double, people: Int): Double = if (people <= 0) 0.0 else tipTotal(bill, tipPercent) / people
 fun discountAmount(price: Double, discountPercent: Double): Double = price * discountPercent / 100.0
 fun discountBase(price: Double, discountPercent: Double): Double = price - discountAmount(price, discountPercent)
 fun discountTax(price: Double, discountPercent: Double, taxPercent: Double): Double = discountBase(price, discountPercent) * taxPercent / 100.0
 fun discountPay(price: Double, discountPercent: Double, taxPercent: Double): Double = discountBase(price, discountPercent) * (1.0 + taxPercent / 100.0)
 fun savingsGrowth(start: Double, monthlyDeposit: Double, apyPercent: Double, years: Int): Pair<Double, Double> {
  val months = years * 12
  var balance = start
  val monthlyRate = apyPercent / 100.0 / 12.0
  repeat(months) { balance = balance * (1.0 + monthlyRate) + monthlyDeposit }
  val invested = start + monthlyDeposit * months
  return Pair(balance, balance - invested)
 }
 fun money(value: Double): String = "$" + "%.2f".format(value)
 fun moneyNoCents(value: Double): String = "$" + "%.0f".format(value)
}
