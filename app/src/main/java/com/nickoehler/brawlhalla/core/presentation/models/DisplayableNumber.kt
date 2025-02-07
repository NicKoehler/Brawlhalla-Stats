package com.nickoehler.brawlhalla.core.presentation.models

import android.icu.text.NumberFormat
import java.util.Locale

data class DisplayableNumber(
    val value: Int,
    val formatted: String,
)

data class DisplayableDouble(
    val value: Double,
    val formatted: String
)

fun Int.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(
        Locale.getDefault()
    )
    return DisplayableNumber(
        this,
        formatted = formatter.format(this)
    )
}

fun Double.toDisplayableDouble(): DisplayableDouble {
    val formatter = NumberFormat.getNumberInstance(
        Locale.getDefault()
    ).apply {
        maximumFractionDigits = 2
        minimumIntegerDigits = 2
    }
    return DisplayableDouble(
        this,
        formatted = formatter.format(this)
    )
}