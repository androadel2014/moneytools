package com.andrewmetry.moneytools
import org.junit.Assert.assertEquals
import org.junit.Test
class CalculatorsTest {
 @Test fun loanReference() { assertEquals(304.22, Calculators.loanMonthlyPayment(10000.0, 6.0, 36), 0.01); assertEquals(951.90, Calculators.loanTotalInterest(10000.0, 6.0, 36), 0.05) }
 @Test fun loanZeroApr() { assertEquals(10000.0 / 36.0, Calculators.loanMonthlyPayment(10000.0, 0.0, 36), 0.001); assertEquals(0.0, Calculators.loanTotalInterest(10000.0, 0.0, 36), 0.001) }
 @Test fun tipReference() { assertEquals(14.40, Calculators.tipAmount(80.0, 18.0), 0.001); assertEquals(94.40, Calculators.tipTotal(80.0, 18.0), 0.001); assertEquals(47.20, Calculators.tipPerPerson(80.0, 18.0, 2), 0.001) }
 @Test fun discountOrderOfOperations() { assertEquals(30.00, Calculators.discountAmount(120.0, 25.0), 0.001); assertEquals(5.40, Calculators.discountTax(120.0, 25.0, 6.0), 0.001); assertEquals(95.40, Calculators.discountPay(120.0, 25.0, 6.0), 0.001) }
 @Test fun savingsGrowthReference() { val r = Calculators.savingsGrowth(1000.0, 200.0, 5.0, 10); assertEquals(32703.47, r.first, 25.0); assertEquals(r.first - (1000.0 + 200.0 * 120), r.second, 0.001) }
}
