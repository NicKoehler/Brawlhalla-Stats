package com.nickoehler.brawlhalla.core.presentation.models

import android.icu.text.NumberFormat
import java.util.Locale

data class DisplayableNumber(
    val value: Int,
    val formatted: String,
)

data class DisplayableFloat(
    val value: Float,
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

fun Float.toDisplayableFloat(): DisplayableFloat {
    val formatter = NumberFormat.getNumberInstance(
        Locale.getDefault()
    ).apply {
        maximumFractionDigits = 2
        minimumIntegerDigits = 2
    }
    return DisplayableFloat(
        this,
        formatted = formatter.format(this)
    )
}