package com.nickoehler.brawlhalla.legends.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.components.CustomShare
import com.nickoehler.brawlhalla.core.presentation.components.WeaponButton
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.legends.domain.LegendDetail
import com.nickoehler.brawlhalla.legends.domain.LegendStat
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsListState
import com.nickoehler.brawlhalla.legends.presentation.components.LegendStatItem
import com.nickoehler.brawlhalla.legends.presentation.models.getStat
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendDetailUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegendDetailScreen(
    state: LegendsListState,
    modifier: Modifier = Modifier,
    onWeaponAction: (WeaponAction) -> Unit = {},
    onLegendAction: (LegendAction) -> Unit = {},
    uiEvent: (UiEvent) -> Unit = {},
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {

        var showBottomSheet by remember { mutableStateOf(false) }
        val legend = state.selectedLegendUi
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                }
            ) {
                if (state.isDetailLoading) {
                    repeat(20) {
                        Box(
                            Modifier
                                .height(20.dp)
                                .padding(20.dp, 5.dp)
                                .clip(CircleShape)
                                .fillMaxSize()
                                .shimmerEffect()
                        )
                    }
                } else if (legend != null) {
                    Text(
                        legend.bioText,
                        modifier = Modifier
                            .padding(20.dp, 0.dp)
                            .verticalScroll(rememberScrollState())
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier.size(0.dp))

            when {
                state.isDetailLoading -> {
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

                else -> {
                    LaunchedEffect(true) {
                        uiEvent(UiEvent.NavigateToList)
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (state.isDetailLoading || legend != null) {

                    IconButton(onClick = { showBottomSheet = true }) {
                        Icon(Icons.Default.Info, "lore")
                    }
                    CustomShare(
                        legend?.bioName,
                        legend?.deepLink,
                        context = LocalContext.current
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    WeaponButton(legend?.weaponOne, onWeaponAction)
                    WeaponButton(legend?.weaponTwo, onWeaponAction)
                }
            }
            if (state.isDetailLoading || legend != null) {
                LegendStat.entries.forEachIndexed { index, stat ->
                    LegendStatItem(
                        stat,
                        legend?.getStat(stat),
                        onLegendAction,
                        delayMillis = 100 * index
                    )
                }
            }
        }
        Spacer(Modifier.size(0.dp))
    }
}

@PreviewLightDark
@Composable
private fun LegendDetailScreenPreview() {
    BrawlhallaTheme {
        Surface {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            ) {
                LegendDetailScreen(
                    state = LegendsListState(
                        selectedLegendUi = legendDetailSample
                            .toLegendDetailUi(),
                    ),
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LegendDetailScreenLoadingPreview() {
    BrawlhallaTheme {
        Surface {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            ) {
                LegendDetailScreen(
                    state = LegendsListState(isDetailLoading = true),
                )
            }
        }
    }
}


internal val legendDetailSample: LegendDetail = LegendDetail(
    legendId = 3,
    legendNameKey = "bodvar",
    bioName = "B\u00f6dvar",
    bioAka = "The Unconquered Viking, The Great Bear",
    bioQuote = "\u201cI speak, you noble vikings, of a warrior who surpassed you all. I tell of a great bear-man who overcame giants and armies, and of how he came to leave our world and challenge the Gods.\u201d",
    bioQuoteAboutAttrib = "\"-The Saga of B\u00f6dvar Bearson, first stanza\"",
    bioQuoteFrom = "\"Listen you nine-mothered bridge troll, I'm coming in, and the first beer I'm drinking is the one in your fist.\"",
    bioQuoteFromAttrib = "\"-B\u00f6dvar to Heimdall, guardian of the gates of Asgard\"",
    bioText = "Born of a viking mother and bear father, B\u00f6dvar grew up feared and mistrusted by his own people.\nB\u00f6dvar's first nemesis was the terrible giant bear Grothnar, his own brother. By defeating Grothnar in a battle that lasted seven days, B\u00f6dvar chose to side with humanity and became the protector of the people of the north. He led his Skandian people against the Witch Queen of Helheim, slew the White Dragon Sorcerer, and lived the life of an all-conquering hero.\nAfter he single-handedly ended the Giant Wars by trapping the fire giant king in his own volcano, B\u00f6dvar sensed his work was done. But he felt doomed to never be taken by the Valkyries to Valhalla because he could never manage to be defeated in battle. So he travelled to Asgard himself, broke down the doors, and let himself in.\nValhalla is everything B\u00f6dvar hoped - an endless reward of feasting and fighting, with himself among its greatest champions.",
    botName = "B\u00f6tvar",
    weaponOne = "Hammer",
    weaponTwo = "Sword",
    strength = "6",
    dexterity = "6",
    defense = "5",
    speed = "5"
)