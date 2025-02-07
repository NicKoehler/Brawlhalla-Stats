package com.nickoehler.brawlhalla.core.presentation.models

import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.TimeZone

data class DisplayableZonedDateTime(
    val value: ZonedDateTime,
    val formatted: String
)

fun LocalDateTime.toZonedDateTime(): ZonedDateTime {
    return ZonedDateTime.of(this, TimeZone.getDefault().toZoneId())
}

fun LocalDateTime.toDisplayableZonedDateTime(): DisplayableZonedDateTime {
    val date = this.toZonedDateTime()
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
    val formattedDateTime = date.format(formatter)

    return DisplayableZonedDateTime(
        value = date,
        formatted = formattedDateTime
    )
}