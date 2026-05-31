package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.screens.legendDetailSample
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun LegendDetailContent(
    legend: LegendDetailUi?,
) {
    if (legend != null) {
        Text(
            legend.bioName,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )
        Text(
            legend.bioAka,
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )
        AsyncImage(
            legend.imageFull,
            contentDescription = legend.bioName,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }
}


@PreviewLightDark
@Composable
private fun LegendDetailContentPreview() {
    BrawlhallaTheme {
        Surface {
            LegendDetailContent(
                legend = legendDetailSample.toLegendDetailUi()
            )
        }
    }
}