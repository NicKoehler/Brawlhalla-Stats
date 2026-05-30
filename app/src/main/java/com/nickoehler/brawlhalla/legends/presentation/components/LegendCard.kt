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
import com.nickoehler.brawlhalla.legends.domain.LegendDetail
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendDetailUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.delay

@Composable
fun LegendCard(
    modifier: Modifier = Modifier,
    legend: LegendDetailUi? = null,
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

                LegendCard(legend = legendSample.toLegendDetailUi())
                LegendCard()
            }
        }
    }
}


internal val legendSample = LegendDetail(

    10,
    "HATTORI",
    "Hattori",
    "Demon Bride",
    "\"The night is freezing\nI sense the spear-bearer nearing.\nIt gets colder still.\"",
    "\"-Verse 761 of Sokan’s One Thousand Verses on the Demon Hattori\"",
    "\"A half-demon ninja who sold her soul to the devil? Well that’s one, frankly rather negative, way of looking at it.\"",
    "\"-Hattori \"",
    "Young Hattori was so gifted with the sword that the Emperor offered a prize of one thousand horses to anyone who could defeat her. For years, new warriors came to court every day only to be defeated. But on the day the Demon Kagima arrived to challenge her, Hattori sensed his malevolent power and fled.  Enraged, the Demon kidnapped Hattori’s three sisters and carried them away to his island kingdom.\nHorrified by the consequences of her actions, Hattori built a boat out of reeds and pursued the Demon. She was lost at sea until a stork lord showed her the passages between the twilight and the night to the Demon’s realm. There Hattori found a bizarre world of talking stones and eight-armed ferrymen. In the City of the Onyx Castle, she met Kagima’s own brother, who offered to give Hattori his strength to defeat his brother, in return for her hand in marriage.\nTrue to his word, the Demon exchanged his own blood with Hattori’s, and Hattori slew Kagima and freed her sisters. She became Queen of the Island Kingdom, but the demon blood burned in her. It drove Hattori to wander the twilight passages until she discovered Valhalla. Through the eternal tournament, Hattori slakes the lust for battle that threatens to consume her.",
    "Bottori",
    "Sword",
    "Spear",
    4,
    6,
    4,
    8
)