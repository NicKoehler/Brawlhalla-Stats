package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.screens.legendDetailSample
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun LegendDetailContent(
    isLoading: Boolean = false,
    legend: LegendDetailUi? = null,
) {
    when {
        isLoading -> {
            Box(
                Modifier
                    .size(height = 50.dp, width = 120.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            Box(
                Modifier
                    .size(height = 20.dp, width = 300.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
                    .shimmerEffect()
            )
            Box(
                Modifier
                    .height(250.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .fillMaxSize()
                    .shimmerEffect()
            )
        }

        legend != null -> {
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
                legend.image,
                contentDescription = legend.bioName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun LegendDetailLoadingContentPreview() {
    BrawlhallaTheme {
        Surface {
            LegendDetailContent(
                true
            )
        }
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