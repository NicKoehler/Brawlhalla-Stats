package com.nickoehler.brawlhalla.ranking.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.components.AnimatedLinearProgressBar
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.ranking.data.mappers.toRegion
import com.nickoehler.brawlhalla.ranking.data.mappers.toTier
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail
import com.nickoehler.brawlhalla.ranking.domain.RankingLegend
import com.nickoehler.brawlhalla.ranking.domain.StatClan
import com.nickoehler.brawlhalla.ranking.domain.StatDetail
import com.nickoehler.brawlhalla.ranking.domain.StatLegend
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.RankingState
import com.nickoehler.brawlhalla.ranking.presentation.components.CustomRankingField
import com.nickoehler.brawlhalla.ranking.presentation.models.StatType
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toStatDetailUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme


@Composable
fun RankingDetailScreen(
    state: RankingState,
    modifier: Modifier = Modifier,
    onRankingAction: (RankingAction) -> Unit = {},
    onUiEvent: (UiEvent) -> Unit = {}
) {
    val playerStat = state.selectedStatDetail
    val playerRanking = state.selectedRankingDetail

    LaunchedEffect(state.isStatDetailLoading) {
        if (playerStat == null && !state.isStatDetailLoading) {
            onUiEvent(UiEvent.PopBack)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(Modifier.size(20.dp))
        if (state.isStatDetailLoading) {
            Box(
                modifier
                    .size(height = 20.dp, width = 40.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            Box(
                modifier
                    .size(height = 30.dp, width = 200.dp)
                    .padding(2.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            IconButton({}) {
                Icon(Icons.Default.Share, null)
            }
        } else if (playerStat != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CustomCard(
                    modifier = Modifier
                        .size(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    contentPadding = 0.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "LV",
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 12.sp,
                        )
                        Text(
                            playerStat.level.toString(),
                            fontSize = 15.sp,
                            lineHeight = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(Modifier.size(10.dp))
                Text(
                    playerStat.name,
                    modifier.weight(1f),
                    fontSize = 30.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                IconButton(
                    {
                        onRankingAction(
                            RankingAction.TogglePlayerFavorites(
                                playerStat.brawlhallaId,
                                playerStat.name
                            )
                        )
                    },

                    ) {
                    Icon(
                        Icons.Default.Star,
                        null,
                        tint = if (state.isStatDetailFavorite) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        },
                    )
                }
            }
            if (playerStat.clan != null) {
                CustomCard(
                    contentPadding = 10.dp,
                    onClick = {
                        onRankingAction(RankingAction.SelectClan(playerStat.clan.clanId))
                    }
                ) {
                    Text(playerStat.clan.clanName)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                AnimatedLinearProgressBar(
                    modifier = Modifier.height(30.dp),
                    indicatorProgress = playerStat.xpPercentage.value,
                    label = playerStat.name
                )
                if (playerStat.nextLevel != null) {
                    Icon(Icons.Default.KeyboardDoubleArrowRight, null)
                    Text(playerStat.nextLevel.toString())
                }
            }
            Text("XP ${playerStat.xp.formatted}")


            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    state.selectedStatType == StatType.General,
                    onClick = { onRankingAction(RankingAction.SelectStatType(StatType.General)) },
                    shape = SegmentedButtonDefaults.itemShape(
                        0,
                        2
                    )
                ) {
                    Text(stringResource(R.string.stats))
                }
                SegmentedButton(
                    state.selectedStatType == StatType.Ranking,
                    onClick = { onRankingAction(RankingAction.SelectStatType(StatType.Ranking)) },
                    shape = SegmentedButtonDefaults.itemShape(1, 2),
                    enabled = state.rankingEnabled
                ) {
                    Text(stringResource(R.string.rankings))
                }
            }

            when (state.selectedStatType) {
                StatType.General -> {
                    CustomRankingField(R.string.games, playerStat.games.formatted)
                    CustomRankingField(R.string.wins, playerStat.wins.formatted)
                    Spacer(Modifier)
                    CustomRankingField(R.string.koBomb, playerStat.koBomb.formatted)
                    CustomRankingField(R.string.damageBomb, playerStat.damageBomb.formatted)
                    Spacer(Modifier)
                    CustomRankingField(R.string.koMine, playerStat.koMine.formatted)
                    CustomRankingField(R.string.damageMine, playerStat.damageMine.formatted)
                    Spacer(Modifier)
                    CustomRankingField(R.string.koSidekick, playerStat.koSidekick.formatted)
                    CustomRankingField(R.string.damageSidekick, playerStat.damageSidekick.formatted)
                    Spacer(Modifier)
                    CustomRankingField(R.string.koSpikeball, playerStat.koSpikeball.formatted)
                    CustomRankingField(
                        R.string.damageSpikeball,
                        playerStat.damageSpikeball.formatted
                    )
                }

                StatType.Ranking -> {
                    CustomRankingField(R.string.rating, playerRanking?.rating?.formatted)
                    CustomRankingField(
                        R.string.peakRating,
                        playerRanking?.peakRating?.formatted
                    )
                    Spacer(Modifier)
                    CustomRankingField(R.string.games, playerRanking?.games?.formatted)
                    CustomRankingField(R.string.wins, playerRanking?.wins?.formatted)
                    Spacer(Modifier)

                }
            }
        }
        Spacer(Modifier)
    }
}

@PreviewLightDark
@Composable
private fun RankingDetailScreenPreview() {
    BrawlhallaTheme {
        Surface {
            RankingDetailScreen(
                state = RankingState(
                    selectedStatDetail = statDetailSample.toStatDetailUi(),
                ),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun RankingDetailScreenLoadingPreview() {
    BrawlhallaTheme {
        Surface {
            RankingDetailScreen(
                state = RankingState(isStatDetailLoading = true),
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun RankingDetailScreenRankingLoadingPreview() {
    BrawlhallaTheme {
        Surface {
            RankingDetailScreen(
                state = RankingState(
                    selectedStatDetail = statDetailSample.toStatDetailUi(),
                    selectedRankingDetail = rankingDetailSample.toRankingDetailUi(),
                    selectedStatType = StatType.Ranking
                ),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun RankingDetailScreenRankingPreview() {
    BrawlhallaTheme {
        Surface {
            RankingDetailScreen(
                state = RankingState(
                    selectedStatDetail = statDetailSample.toStatDetailUi(),
                    isRankingDetailLoading = true,
                    selectedStatType = StatType.Ranking
                ),
            )
        }
    }
}

internal val statDetailSample = StatDetail(
    brawlhallaId = 2316541,
    name = "NicKoehler",
    xp = 4261524,
    level = 100,
    xpPercentage = 0f,
    games = 36261,
    wins = 23924,
    damageBomb = 208300,
    damageMine = 173721,
    damageSpikeball = 128864,
    damageSidekick = 84351,
    hitSnowball = 852,
    koBomb = 1851,
    koMine = 1288,
    koSpikeball = 1212,
    koSidekick = 27,
    koSnowball = 266,
    legends = listOf(
        StatLegend(
            5,
            "orion",
            1456241,
            1224326,
            7811,
            6054,
            348,
            183,
            540349,
            3089,
            1873,
            147318,
            17904,
            524464,
            707754,
            47388,
            1007,
            482,
            2670,
            3340,
            281,
            184119,
            200239,
            325098,
            59,
            0.6792734706714892
        ),
        StatLegend(
            29,
            "wu shang",
            405857,
            319666,
            2251,
            1606,
            124,
            24,
            135574,
            1134,
            753,
            71665,
            10000,
            177017,
            124642,
            13686,
            464,
            219,
            944,
            473,
            110,
            52951,
            32826,
            82934,
            33,
            0.23316239316239315
        ),
        StatLegend(
            7,
            "gnash",
            306796,
            210941,
            1765,
            1019,
            73,
            3,
            92623,
            815,
            564,
            55168,
            8147,
            122921,
            101359,
            10866,
            312,
            186,
            774,
            375,
            86,
            32966,
            24283,
            61487,
            29,
            0.1941846278975193
        ),
        // Add the rest of the StatLegend objects here...
        StatLegend(
            32,
            "cross",
            316676,
            232804,
            1827,
            1169,
            90,
            5,
            99789,
            856,
            570,
            42983,
            12733,
            126464,
            115168,
            12988,
            274,
            176,
            635,
            644,
            71,
            35258,
            34174,
            65048,
            29,
            0.9182594550630337
        ),
    ),
    clan = StatClan(
        clanName = "PiediniYumiko",
        clanId = 1754020,
        clanXp = 141563,
        personalXp = 4425,
    )
)


internal val rankingDetailSample =
    RankingDetail(

        name = "",
        brawlhallaId = 2316541,
        rating = 0,
        peakRating = 0,
        tier = "none".toTier(),
        wins = 0,
        games = 0,
        region = "none".toRegion(),
        globalRank = 0,
        regionRank = 0,
        legends =
        listOf(
            RankingLegend(
                3,
                "bodvar",
                758,
                0,
                "Tin 2".toTier(),
                0,
                0
            ),
            RankingLegend(
                4,
                "cassidy",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                5,
                "orion",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                19,
                "brynn",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                22,
                "ulgrim",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                25,
                "diana",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                18,
                "ember",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                16,
                "teros",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                14,
                "ada",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                29,
                "wu shang",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                28,
                "kor",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                35,
                "mordex",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                38,
                "caspian",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                31,
                "ragnir",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                46,
                "rayman",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                26,
                "jhala",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            ),
            RankingLegend(
                36,
                "yumiko",
                750,
                0,
                "Tin 1".toTier(),
                0,
                0
            )
        ),

        teams = emptyList()
    )