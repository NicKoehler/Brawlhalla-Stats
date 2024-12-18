package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun CustomLegendField(@StringRes key: Int, value: String?) {
    Row {
        Text(stringResource(key))
        Spacer(Modifier.weight(1f))
        if (value != null) {
            Text(value)
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


@PreviewLightDark
@Composable
private fun CustomRankingFieldPreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                CustomLegendField(R.string.games, 100.toString())
                CustomLegendField(R.string.games, null)
            }
        }
    }

}