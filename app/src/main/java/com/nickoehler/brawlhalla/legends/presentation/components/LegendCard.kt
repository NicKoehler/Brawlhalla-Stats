package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.components.WeaponButton
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.models.LegendUi
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.legends.domain.Legend
import com.nickoehler.brawltool.presentation.ui.components.CustomCard

@Composable
fun LegendCard(
    legend: LegendUi,
    onLegendAction: (LegendAction) -> Unit,
    onWeaponAction: (WeaponAction) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomCard(
        onClick = { onLegendAction(LegendAction.SelectLegend(legend.legendId)) },
        modifier = modifier,
    ) {
        AsyncImage(
            legend.image,
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.surfaceBright)
        )
        Spacer(Modifier.size(20.dp))
        Column(Modifier.weight(1f)) {
            Text(
                legend.bioName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                legend.bioAka,
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground,
            )

        }
        Column(verticalArrangement = Arrangement.SpaceAround) {
            WeaponButton(
                legend.weaponOne,
                { onWeaponAction(WeaponAction.Click(legend.weaponOne)) })
            Spacer(modifier = Modifier.size(10.dp))
            WeaponButton(legend.weaponTwo,
                { onWeaponAction(WeaponAction.Click(legend.weaponTwo)) })
        }
    }
}

@PreviewLightDark
@Composable
private fun LegendCardPreview() {
    BrawlhallaTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            LegendCard(legendSample.toLegendUi(), {}, {})
        }
    }

}


internal val legendSample = Legend(
    3,
    "bodvar",
    "Bodvar",
    "The Unconquered Viking, The Great Bear",
    "Hammer",
    "Sword",
    "6",
    "6",
    "5",
    "5",
)