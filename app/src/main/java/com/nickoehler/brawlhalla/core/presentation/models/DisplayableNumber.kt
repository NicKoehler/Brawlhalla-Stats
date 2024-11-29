package com.nickoehler.brawlhalla.core.presentation.models

import android.icu.text.NumberFormat
import java.util.Locale

data class DisplayableNumber(
    val value: Int,
    val formatted: String,
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

