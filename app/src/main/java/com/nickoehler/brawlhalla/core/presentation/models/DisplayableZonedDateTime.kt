package com.nickoehler.brawlhalla.core.presentation.models

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.TimeZone

data class DisplayableZonedDateTime(
    val value: ZonedDateTime,
    val formatted: String
)

data class DisplayableTime(
    val value: Long,
    val formatted: String
)

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)
}

fun Long.toLocalTime(): LocalTime {
    return LocalTime.ofSecondOfDay(this)
}

fun Long.toDisplayableTime(): DisplayableTime {

    val days = this.floorDiv(86400)
    val hours = this.mod(86400).floorDiv(3600)
    val minutes = this.mod(3600).floorDiv(60)
    val seconds = this.mod(60)

    val formatted = mutableListOf<String>()

    if (days > 0) {
        formatted.add("D:$days")
    }

    if (hours > 0) {
        formatted.add("H:$hours")
    }
    if (minutes > 0) {
        formatted.add("M:$minutes")

    }
    if (seconds > 0) {
        formatted.add("S:$seconds")
    }

    return DisplayableTime(
        this,
        formatted.joinToString(" ")
    )
}

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