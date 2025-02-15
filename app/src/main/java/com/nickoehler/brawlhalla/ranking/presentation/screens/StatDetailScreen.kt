package com.nickoehler.brawlhalla.ranking.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.core.presentation.util.toString
import com.nickoehler.brawlhalla.legends.presentation.models.RankingModalType
import com.nickoehler.brawlhalla.ranking.data.mappers.toRegion
import com.nickoehler.brawlhalla.ranking.data.mappers.toTier
import com.nickoehler.brawlhalla.ranking.domain.Ranking
import com.nickoehler.brawlhalla.ranking.domain.RankingDetail
import com.nickoehler.brawlhalla.ranking.domain.RankingLegend
import com.nickoehler.brawlhalla.ranking.domain.Region
import com.nickoehler.brawlhalla.ranking.domain.StatClan
import com.nickoehler.brawlhalla.ranking.domain.StatDetail
import com.nickoehler.brawlhalla.ranking.domain.StatLegend
import com.nickoehler.brawlhalla.ranking.domain.Tier
import com.nickoehler.brawlhalla.ranking.presentation.StatDetailAction
import com.nickoehler.brawlhalla.ranking.presentation.StatDetailState
import com.nickoehler.brawlhalla.ranking.presentation.components.CustomLevelProgressBar
import com.nickoehler.brawlhalla.ranking.presentation.components.CustomRankingField
import com.nickoehler.brawlhalla.ranking.presentation.components.LegendRankingItem
import com.nickoehler.brawlhalla.ranking.presentation.components.LegendRankingItemDetail
import com.nickoehler.brawlhalla.ranking.presentation.components.LegendStatItem
import com.nickoehler.brawlhalla.ranking.presentation.components.LegendStatItemDetail
import com.nickoehler.brawlhalla.ranking.presentation.components.TeamItem
import com.nickoehler.brawlhalla.ranking.presentation.components.TeamItemDetail
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatType
import com.nickoehler.brawlhalla.ranking.presentation.models.getTeamMateId
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toStatDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.util.toString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.plcoding.cryptotracker.core.presentation.util.ObserveAsEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatDetailScreen(
    state: StatDetailState,
    onBack: () -> Unit,
    onPlayerSelection: (Int) -> Unit,
    onStatDetailAction: (StatDetailAction) -> Unit,
    modifier: Modifier = Modifier,
    events: Flow<UiEvent> = emptyFlow(),
) {
    val density = LocalDensity.current
    val context = LocalContext.current
    val playerStat = state.selectedStatDetail
    val playerRanking = state.selectedRankingDetail
    var screenWidth by remember { mutableStateOf(0.dp) }
    val itemSize = 200.dp
    val columns by remember {
        derivedStateOf {
            screenWidth.div(itemSize).toInt().coerceAtLeast(2)
        }
    }

    ObserveAsEvents(events) { event ->
        when (event) {
            is UiEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
                onBack()
            }

            is UiEvent.Message -> {
                Toast.makeText(
                    context,
                    event.message.toString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }

    if (state.modalType != null) {
        ModalBottomSheet(
            onDismissRequest = {
                onStatDetailAction(
                    StatDetailAction.SelectRankingModalType(null)
                )
            }
        ) {
            when (state.modalType) {

                is RankingModalType.StatLegend -> {
                    LegendStatItemDetail(
                        state.modalType.statLegend,
                        columns = columns,
                    )
                }

                is RankingModalType.Team -> {
                    TeamItemDetail(
                        state.modalType.team,
                        columns = columns,
                        {
                            if (state.selectedStatDetail != null) {
                                onPlayerSelection(
                                    state.modalType.team.getTeamMateId(state.selectedStatDetail.brawlhallaId)
                                )
                            }
                        }
                    )
                }

                is RankingModalType.RankingLegend -> {
                    LegendRankingItemDetail(
                        state.modalType.legend,
                        columns = columns,
                    )
                }
            }
        }
    }
    Scaffold(
        modifier = modifier.onGloballyPositioned { layoutCoordinates ->
            val widthInPx = layoutCoordinates.size.width
            screenWidth = with(density) { widthInPx.toDp() }
        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(R.string.back),
                        )
                    }
                },
                actions = {
                    IconButton({
                        if (playerStat != null) {
                            onStatDetailAction(
                                StatDetailAction.TogglePlayerFavorites(
                                    playerStat.brawlhallaId,
                                    playerStat.name
                                )
                            )
                        }
                    }) {
                        Icon(
                            Icons.Default.Favorite,
                            null,
                            tint = if (state.isStatDetailFavorite) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
                        )
                    }

                },
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (state.isStatDetailLoading) {
                            Box(
                                modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .padding(horizontal = 30.dp, vertical = 2.dp)
                                    .clip(CircleShape)
                                    .shimmerEffect()
                            )
                        } else if (playerStat != null) {
                            Text(
                                playerStat.name,
                                fontSize = 30.sp,
                                lineHeight = 30.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }


                }
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            item(span = { GridItemSpan(columns) }) {
                StatDetailHeader(state, onStatDetailAction)
            }
            stats(state, playerStat, onStatDetailAction, playerRanking)
        }
    }
}

private fun LazyGridScope.stats(
    state: StatDetailState,
    playerStat: StatDetailUi?,
    onStatDetailAction: (StatDetailAction) -> Unit,
    playerRanking: RankingDetailUi?,
) {
    when (state.selectedStatType) {
        StatType.General -> {
            when (state.selectedStatFilterType) {
                StatFilterType.Stat -> generalStat(playerStat)
                StatFilterType.Legends -> generalLegendStat(playerStat, onStatDetailAction)
            }
        }

        StatType.Ranking -> {
            when (state.selectedRankingFilterType) {
                RankingFilterType.Stat -> rankingStat(playerRanking)
                RankingFilterType.Legends -> rankingLegends(playerRanking, onStatDetailAction)
                RankingFilterType.Teams -> rankingTeams(playerRanking, onStatDetailAction)
            }
        }
    }
}

private fun LazyGridScope.rankingTeams(
    playerRanking: RankingDetailUi?,
    onStatDetailAction: (StatDetailAction) -> Unit
) {
    if (playerRanking != null) {
        items(
            playerRanking.teams,
            { "${it.brawlhallaIdOne}${it.brawlhallaIdTwo}" },
        ) { team ->
            TeamItem(
                team,
                modifier = Modifier.animateItem()
            ) {
                onStatDetailAction(
                    StatDetailAction.SelectRankingModalType(
                        RankingModalType.Team(team)
                    )
                )
            }
        }
    }
}

private fun LazyGridScope.rankingLegends(
    playerRanking: RankingDetailUi?,
    onStatDetailAction: (StatDetailAction) -> Unit
) {
    if (playerRanking != null) {
        items(
            playerRanking.legends, { it.legendId },
        ) { legend ->
            LegendRankingItem(
                legend,
                onClick = {
                    onStatDetailAction(
                        StatDetailAction.SelectRankingModalType(
                            RankingModalType.RankingLegend(legend)
                        )
                    )
                },
                modifier = Modifier.animateItem()
            )
        }
    }
}

private fun LazyGridScope.rankingStat(
    playerRanking: RankingDetailUi?,
) {
    item {
        CustomRankingField(
            R.string.rating,
            playerRanking?.rating?.formatted,
            modifier = Modifier.animateItem()

        )
    }
    item {
        CustomRankingField(
            R.string.peakRating,
            playerRanking?.peakRating?.formatted,
            modifier = Modifier.animateItem()

        )
    }
    item {
        CustomRankingField(
            R.string.games,
            playerRanking?.games?.formatted,
            modifier = Modifier.animateItem()

        )
    }
    item {
        CustomRankingField(
            R.string.wins,
            playerRanking?.wins?.formatted,
            modifier = Modifier.animateItem()

        )
    }

    item {
        CustomRankingField(
            R.string.estimatedGlory,
            playerRanking?.estimatedGlory?.formatted,
            modifier = Modifier.animateItem()
        )
    }

    item {
        CustomRankingField(
            R.string.estimatedEloReset,
            playerRanking?.estimatedEloReset?.formatted,
            modifier = Modifier.animateItem()
        )
    }
}

private fun LazyGridScope.generalLegendStat(
    playerStat: StatDetailUi?,
    onStatDetailAction: (StatDetailAction) -> Unit
) {
    if (playerStat?.legends?.isNotEmpty() == true) {
        items(
            playerStat.legends, { it.legendId },
        ) { legend ->
            LegendStatItem(
                legend,
                onStatDetailAction = {
                    onStatDetailAction(
                        StatDetailAction.SelectRankingModalType(
                            RankingModalType.StatLegend(legend)
                        )
                    )
                },
                modifier = Modifier.animateItem()
            )
        }
    }
}

private fun LazyGridScope.generalStat(playerStat: StatDetailUi?) {
    item {
        CustomRankingField(
            R.string.games,
            playerStat?.games?.formatted,
            modifier = Modifier.animateItem()

        )
    }
    item {
        CustomRankingField(
            R.string.wins,
            playerStat?.wins?.formatted,
            modifier = Modifier.animateItem()
        )
    }

    item {
        CustomRankingField(
            R.string.koBomb,
            playerStat?.koBomb?.formatted,
            modifier = Modifier.animateItem()

        )
    }
    item {
        CustomRankingField(
            R.string.damageBomb,
            playerStat?.damageBomb?.formatted,
            modifier = Modifier.animateItem()

        )
    }

    item {
        CustomRankingField(
            R.string.koMine,
            playerStat?.koMine?.formatted,
            modifier = Modifier.animateItem()

        )
    }
    item {
        CustomRankingField(
            R.string.damageMine,
            playerStat?.damageMine?.formatted,
            modifier = Modifier.animateItem()

        )
    }

    item {
        CustomRankingField(
            R.string.koSpikeball,
            playerStat?.koSpikeball?.formatted,
            modifier = Modifier.animateItem()

        )
    }
    item {
        CustomRankingField(
            R.string.damageSpikeball,
            playerStat?.damageSpikeball?.formatted,
            modifier = Modifier.animateItem()

        )
    }

    item {
        CustomRankingField(
            R.string.koSidekick,
            playerStat?.koSidekick?.formatted,
            modifier = Modifier.animateItem()

        )
    }
    item {
        CustomRankingField(
            R.string.damageSidekick,
            playerStat?.damageSidekick?.formatted,
            modifier = Modifier.animateItem()

        )
    }

    item {
        CustomRankingField(
            R.string.koSnowball,
            playerStat?.koSnowball?.formatted,
            modifier = Modifier.animateItem()

        )
    }

    item {
        CustomRankingField(
            R.string.hitSnowball,
            playerStat?.hitSnowball?.formatted,
            modifier = Modifier.animateItem()
        )
    }
}

@Composable
private fun StatDetailHeader(
    state: StatDetailState,
    onStatDetailAction: (StatDetailAction) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (state.isStatDetailLoading) {
            Box(
                Modifier
                    .size(height = 48.dp, width = 150.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            Box(
                Modifier
                    .size(height = 16.dp, width = 300.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            Box(
                Modifier
                    .size(height = 26.dp, width = 150.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        } else if (state.selectedStatDetail != null) {
            if (state.selectedStatDetail.clan != null) {
                CustomCard(
                    contentPadding = 10.dp,
                    onClick = {
                        onStatDetailAction(
                            StatDetailAction.SelectClan(
                                state.selectedStatDetail.clan.clanId
                            )
                        )
                    }
                ) {
                    Text(state.selectedStatDetail.clan.clanName)
                }
            }

            CustomLevelProgressBar(
                state.selectedStatDetail.xpPercentage.value,
                state.selectedStatDetail.name,
                state.selectedStatDetail.level,
                state.selectedStatDetail.nextLevel,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Text("XP ${state.selectedStatDetail.xp.formatted}")
        }

        SingleChoiceSegmentedButtonRow {
            StatType.entries.forEachIndexed { index, statType ->
                SegmentedButton(
                    state.selectedStatType == statType,
                    onClick = {
                        onStatDetailAction(
                            StatDetailAction.SelectStatType(
                                statType
                            )
                        )
                    },
                    shape = SegmentedButtonDefaults.itemShape(
                        index,
                        StatType.entries.size
                    ),
                    icon = {
                        Icon(
                            when (statType) {
                                StatType.General -> Icons.Default.QueryStats
                                StatType.Ranking -> Icons.Default.Poll
                            },
                            null
                        )
                    }
                ) {
                    Text(
                        stringResource(
                            when (statType) {
                                StatType.General -> R.string.stats
                                StatType.Ranking -> R.string.rankings
                            }
                        )
                    )
                }
            }
        }

        AnimatedContent(
            state.selectedStatType,
        ) { stateType ->
            SingleChoiceSegmentedButtonRow {
                when (stateType) {
                    StatType.General -> {
                        StatFilterType.entries.forEachIndexed { index, statType ->
                            SegmentedButton(
                                selected = state.selectedStatFilterType == statType,
                                shape = SegmentedButtonDefaults.itemShape(
                                    index,
                                    StatFilterType.entries.size
                                ),
                                onClick = {
                                    onStatDetailAction(
                                        StatDetailAction.SelectStatFilterType(
                                            statType
                                        )
                                    )
                                },
                                label = {
                                    Text(
                                        stringResource(
                                            when (statType) {
                                                StatFilterType.Stat -> R.string.stats
                                                StatFilterType.Legends -> R.string.legends
                                            }
                                        )
                                    )
                                },
                            )
                        }
                    }

                    StatType.Ranking -> {
                        RankingFilterType.entries.forEachIndexed { index, statType ->
                            SegmentedButton(
                                selected = state.selectedRankingFilterType == statType,
                                shape = SegmentedButtonDefaults.itemShape(
                                    index,
                                    RankingFilterType.entries.size
                                ),
                                onClick = {
                                    onStatDetailAction(
                                        StatDetailAction.SelectRankingFilterType(
                                            statType
                                        )
                                    )
                                },
                                label = {
                                    Text(
                                        stringResource(
                                            when (statType) {
                                                RankingFilterType.Legends -> R.string.legends
                                                RankingFilterType.Teams -> R.string.teams
                                                RankingFilterType.Stat -> R.string.stats
                                            }
                                        )
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun RankingDetailScreenPreview() {
    BrawlhallaTheme {
        Surface {
            StatDetailScreen(
                state = StatDetailState(
                    selectedStatDetail = statDetailSample.toStatDetailUi(),
                ),
                {},
                {},
                {}
            )
        }
    }
}

@Preview
@Composable
private fun RankingDetailScreenLoadingPreview() {
    BrawlhallaTheme {
        Surface {
            StatDetailScreen(
                state = StatDetailState(isStatDetailLoading = true),
                {},
                {},
                {}
            )
        }
    }
}


@Preview
@Composable
private fun RankingDetailScreenRankingLoadingPreview() {
    BrawlhallaTheme {
        Surface {
            StatDetailScreen(
                state = StatDetailState(
                    selectedStatDetail = statDetailSample.toStatDetailUi(),
                    selectedRankingDetail = rankingDetailSample.toRankingDetailUi(),
                    selectedStatType = StatType.Ranking
                ),
                {},
                {},
                {}
            )
        }
    }
}

@Preview
@Composable
private fun RankingDetailScreenRankingPreview() {
    BrawlhallaTheme {
        Surface {
            StatDetailScreen(
                state = StatDetailState(
                    selectedStatDetail = statDetailSample.toStatDetailUi(),
                    isRankingDetailLoading = true,
                    selectedStatType = StatType.Ranking
                ),
                {},
                {},
                {}
            )
        }
    }
}

internal val statDetailSample = StatDetail(
    brawlhallaId = 2316541,
    name = "NicKoehler",
    xp = 4261524,
    level = 100,
    xpPercentage = 0.0,
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
            540349L,
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
            0.6792734714892
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
            135574L,
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
            92623L,
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
            99789L,
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

        teams = listOf(
            Ranking.RankingTeam(
                1, "Gesu+Maria", 1, 2,
                rating = 2000,
                tier = Tier.DIAMOND,
                games = 234,
                wins = 234,
                region = Region.EU,
                peakRating = 2000,
            )
        ),
        estimatedGlory = 1023,
        estimatedEloReset = 1800
    )