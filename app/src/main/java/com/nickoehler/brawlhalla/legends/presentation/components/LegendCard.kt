package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.LegendImage
import com.nickoehler.brawlhalla.core.presentation.components.WeaponButton
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.legends.domain.Legend
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.models.LegendUi
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.delay

@Composable
fun LegendCard(
    modifier: Modifier = Modifier,
    legend: LegendUi? = null,
    onLegendAction: (LegendAction) -> Unit = {},
    onWeaponAction: (WeaponAction) -> Unit = {}
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100L)
        visible = true
    }

    val animatedFloat by animateFloatAsState(if (visible) 1f else 0.9f)

    CustomCard(
        onClick = { if (legend != null) onLegendAction(LegendAction.SelectLegend(legend.legendId)) },
        modifier = modifier
            .scale(animatedFloat)
            .alpha(animatedFloat),
    ) {
        LegendImage(legend?.bioName, legend?.image)
        Spacer(Modifier.size(20.dp))
        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.Top,
        ) {
            if (legend != null) {
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
            } else {
                Box(
                    Modifier
                        .height(20.dp)
                        .padding(end = 40.dp)
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .shimmerEffect()
                )
                Spacer(Modifier.size(20.dp))
                Box(
                    Modifier
                        .height(10.dp)
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .shimmerEffect()
                )
                Spacer(Modifier.size(10.dp))

            }

        }
        Spacer(Modifier.size(20.dp))
        Column(verticalArrangement = Arrangement.SpaceAround) {
            WeaponButton(
                weapon = legend?.weaponOne,
                onClick = {
                    if (legend != null) {
                        onWeaponAction(WeaponAction.Click(legend.weaponOne))
                    }
                })
            Spacer(modifier = Modifier.size(10.dp))
            WeaponButton(
                weapon = legend?.weaponTwo,
                onClick = {
                    if (legend != null) {
                        onWeaponAction(WeaponAction.Click(legend.weaponTwo))
                    }
                })
        }
    }
}

@PreviewLightDark
@Composable
private fun LegendCardPreview() {
    BrawlhallaTheme {
        Surface {

            Column {

                LegendCard(legend = legendSample.toLegendUi())
                LegendCard()
            }
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