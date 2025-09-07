package com.nickoehler.brawlhalla.core.presentation.models

import android.icu.text.NumberFormat
import java.util.Locale

data class DisplayableInt(
    val value: Int,
    val formatted: String,
)

data class DisplayableLong(
    val value: Long,
    val formatted: String,
)

data class DisplayableDouble(
    val value: Double,
    val formatted: String
)

fun Int.toDisplayableNumber(): DisplayableInt {
    val formatter = NumberFormat.getNumberInstance(
        Locale.getDefault()
    )
    return DisplayableInt(
        this,
        formatted = formatter.format(this)
    )
}

fun Long.toDisplayableNumber(): DisplayableLong {
    val formatter = NumberFormat.getNumberInstance(
        Locale.getDefault()
    )
    return DisplayableLong(
        this,
        formatted = formatter.format(this)
    )
}


fun Double.toDisplayableNumber(): DisplayableDouble {
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