package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun CustomRankingField(@StringRes key: Int, value: String?, color: Color =  MaterialTheme.colorScheme. surfaceContainer, modifier: Modifier = Modifier) {
    CustomCard(
        modifier = modifier,
        color = color,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(key),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            if (value != null) {
                Text(
                    text = value,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            } else {
                Box(
                    Modifier
                        .size(40.dp, 20.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun CustomRankingFieldPreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                CustomRankingField(R.string.games, 100.toString())
                CustomRankingField(R.string.games, null)
            }
        }
    }

}