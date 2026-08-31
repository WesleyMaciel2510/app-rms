package com.example.rms.core.formatting

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Currency
import java.util.Locale

data class Money(
    val amount: BigDecimal,
    val currency: Currency = Currency.getInstance("BRL")
) {
    companion object {
        fun fromLong(value: Long, currency: Currency = Currency.getInstance("BRL")): Money {
            return Money(BigDecimal.valueOf(value).movePointLeft(2), currency)
        }

        fun fromString(value: String, currency: Currency = Currency.getInstance("BRL")): Money {
            return Money(BigDecimal(value), currency)
        }

        val ZERO: Money
            get() = Money(BigDecimal.ZERO)
    }

    operator fun plus(other: Money): Money {
        require(currency == other.currency) { "Cannot add different currencies" }
        return Money(amount.add(other.amount), currency)
    }

    operator fun minus(other: Money): Money {
        require(currency == other.currency) { "Cannot subtract different currencies" }
        return Money(amount.subtract(other.amount), currency)
    }

    operator fun times(multiplier: Int): Money {
        return Money(amount.multiply(BigDecimal.valueOf(multiplier.toLong())), currency)
    }

    operator fun div(divisor: Int): Money {
        return Money(amount.divide(BigDecimal.valueOf(divisor.toLong()), 2, RoundingMode.HALF_UP), currency)
    }

    fun isZero(): Boolean = amount.compareTo(BigDecimal.ZERO) == 0
    fun isPositive(): Boolean = amount.compareTo(BigDecimal.ZERO) > 0
    fun isNegative(): Boolean = amount.compareTo(BigDecimal.ZERO) < 0
    fun isGreaterThan(other: Money): Boolean {
        require(currency == other.currency) { "Cannot compare different currencies" }
        return amount.compareTo(other.amount) > 0
    }

    fun isLessThan(other: Money): Boolean {
        require(currency == other.currency) { "Cannot compare different currencies" }
        return amount.compareTo(other.amount) < 0
    }

    override fun toString(): String = MoneyFormatter.format(this)
}

object MoneyFormatter {
    private val brazilLocale = Locale("pt", "BR")
    
    fun format(money: Money): String {
        val formatter = java.text.NumberFormat.getCurrencyInstance(brazilLocale)
        formatter.currency = money.currency
        formatter.maximumFractionDigits = 2
        formatter.minimumFractionDigits = 2
        return formatter.format(money.amount)
    }

    fun formatWithoutSymbol(money: Money): String {
        val formatter = java.text.DecimalFormat("#,##0.00", java.text.DecimalFormatSymbols(brazilLocale))
        return formatter.format(money.amount)
    }

    fun formatAmount(amount: BigDecimal): String {
        val formatter = java.text.NumberFormat.getCurrencyInstance(brazilLocale)
        formatter.currency = Currency.getInstance("BRL")
        formatter.maximumFractionDigits = 2
        formatter.minimumFractionDigits = 2
        return formatter.format(amount)
    }
}