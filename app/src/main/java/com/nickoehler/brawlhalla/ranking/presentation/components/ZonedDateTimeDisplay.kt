package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun ZonedDateTimeDisplay(zonedDateTime: ZonedDateTime, modifier: Modifier = Modifier) {
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)

    val formattedDateTime = zonedDateTime.format(formatter)

    Text(
        modifier = modifier,
        text = formattedDateTime
    )
}