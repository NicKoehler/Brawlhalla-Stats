package com.nickoehler.brawlhalla.ranking.presentation.screens

import android.content.ClipData
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.core.presentation.components.CustomSortDropDownMenu
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.core.presentation.util.ObserveAsEvents
import com.nickoehler.brawlhalla.core.presentation.util.toString
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
import com.nickoehler.brawlhalla.ranking.presentation.models.GeneralRankingSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingModalType
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.StatFilterType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatLegendSortType
import com.nickoehler.brawlhalla.ranking.presentation.models.StatType
import com.nickoehler.brawlhalla.ranking.presentation.models.getTeamMateId
import com.nickoehler.brawlhalla.ranking.presentation.models.toIcon
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toStatDetailUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toStringResource
import com.nickoehler.brawlhalla.ranking.presentation.util.toString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatDetailScreen(
    state: StatDetailState,
    onBack: () -> Unit,
    onPlayerSelection: (Long) -> Unit,
    onClanSelection: (Long) -> Unit,
    onLegendSelection: (Long) -> Unit,
    onStatDetailAction: (StatDetailAction) -> Unit,
    modifier: Modifier = Modifier,
    events: Flow<UiEvent> = emptyFlow(),
) {
    val density = LocalDensity.current
    val context = LocalContext.current
    val clipboard = LocalClipboard.current
    val haptic = LocalHapticFeedback.current
    val playerStat = state.selectedStatDetail
    val playerRanking = state.selectedRankingDetail
    var screenWidth by remember { mutableStateOf(0.dp) }
    val itemSize = 200.dp
    val columns by remember {
        derivedStateOf {
            screenWidth.div(itemSize).toInt().coerceAtLeast(2)
        }
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())


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
                        legend = state.modalType.statLegend,
                        columns = columns,
                        onClick = {
                            onLegendSelection(
                                state.modalType.statLegend.legendId
                            )
                            onStatDetailAction(
                                StatDetailAction.SelectRankingModalType(null)
                            )
                        }
                    )
                }

                is RankingModalType.Team -> {
                    TeamItemDetail(
                        team = state.modalType.team,
                        columns = columns,
                        onClick = {
                            if (state.selectedStatDetail != null) {
                                onPlayerSelection(
                                    state.modalType.team.getTeamMateId(state.selectedStatDetail.brawlhallaId)
                                )
                                onStatDetailAction(
                                    StatDetailAction.SelectRankingModalType(null)
                                )
                            }
                        }
                    )
                }

                is RankingModalType.RankingLegend -> {
                    LegendRankingItemDetail(
                        legend = state.modalType.legend,
                        columns = columns,
                        onClick = {
                            onLegendSelection(
                                state.modalType.legend.legendId
                            )
                            onStatDetailAction(
                                StatDetailAction.SelectRankingModalType(null)
                            )
                        }
                    )
                }
            }
        }
    }
    Scaffold(
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                val widthInPx = layoutCoordinates.size.width
                screenWidth = with(density) { widthInPx.toDp() }
            }
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                scrollBehavior = scrollBehavior,
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
                            stringResource(R.string.favorites),
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
                        verticalAlignment = Alignment.Bottom,
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
                            BasicText(
                                text = playerStat.name,
                                style = TextStyle.Default.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontSize = 30.sp,
                                    lineHeight = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                ),
                                softWrap = false,
                                modifier = Modifier.widthIn(max = 250.dp),
                                autoSize = TextAutoSize.StepBased(
                                    minFontSize = 12.sp,
                                    maxFontSize = 30.sp,
                                    stepSize = 1.sp
                                )
                            )
                            Spacer(Modifier.width(10.dp))
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                modifier = Modifier
                                    .padding(bottom = 2.dp)
                            ) {
                                Text(
                                    "id",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                    softWrap = false,
                                )
                                Icon(
                                    modifier = Modifier
                                        .graphicsLayer {
                                            rotationX = 180f
                                        }
                                        .padding(top = 4.dp)
                                        .size(15.dp)
                                        .clickable {
                                            clipboard.nativeClipboard.setPrimaryClip(
                                                ClipData.newPlainText(
                                                    "",
                                                    AnnotatedString(
                                                        state.selectedStatDetail.brawlhallaId.toString()
                                                    )
                                                )
                                            )
                                            haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                        },
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = stringResource(R.string.copy_id),
                                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            modifier = modifier
                .padding(padding)
                .padding(horizontal = Spacing.scaffoldWindowInsets - 8.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            columns = GridCells.Fixed(columns),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            item(span = { GridItemSpan(columns) }) {
                StatDetailHeader(state, onClanSelection, onStatDetailAction)
            }
            stats(state, columns, playerStat, onStatDetailAction, playerRanking)
        }
    }
}

private fun LazyGridScope.stats(
    state: StatDetailState,
    columns: Int,
    playerStat: StatDetailUi?,
    onStatDetailAction: (StatDetailAction) -> Unit,
    playerRanking: RankingDetailUi?,
) {
    when (state.selectedStatType) {
        StatType.Stats -> {
            when (state.selectedStatFilterType) {
                StatFilterType.General -> generalStat(playerStat)
                StatFilterType.Legends -> generalLegendStat(
                    state.statLegendSortType,
                    playerStat,
                    columns,
                    state.statLegendSortReversed,
                    onStatDetailAction,
                )
            }
        }

        StatType.Rankings -> {
            when (state.selectedRankingFilterType) {
                RankingFilterType.Stat -> rankingStat(playerRanking)
                RankingFilterType.Legends -> rankingLegends(
                    playerRanking,
                    state.rankedLegendSortType,
                    state.rankedLegendSortReversed,
                    columns,
                    onStatDetailAction
                )

                RankingFilterType.Teams -> rankingTeams(
                    playerRanking,
                    state.teamSortType,
                    state.teamSortReversed,
                    columns,
                    onStatDetailAction
                )
            }
        }
    }
}

private fun LazyGridScope.rankingTeams(
    playerRanking: RankingDetailUi?,
    sortType: GeneralRankingSortType,
    reversed: Boolean,
    columns: Int,
    onStatDetailAction: (StatDetailAction) -> Unit
) {
    if (playerRanking != null) {
        item(span = { GridItemSpan(columns) }) {
            CustomRankedDropDown(
                sortType,
                reversed,
                onReverse = { onStatDetailAction(StatDetailAction.TeamSortTypeReversed) },
                onSort = {
                    onStatDetailAction(
                        StatDetailAction.SortBy(RankingSortType.Team(it))
                    )
                }
            )
        }
        items(
            playerRanking.teams,
            { team -> "${team.brawlhallaIdOne}-${team.brawlhallaIdTwo}" }
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
    sortType: GeneralRankingSortType,
    reversed: Boolean,
    columns: Int,
    onStatDetailAction: (StatDetailAction) -> Unit
) {
    if (playerRanking != null) {
        item(span = { GridItemSpan(columns) }) {
            CustomRankedDropDown(
                sortType,
                reversed,
                onReverse = { onStatDetailAction(StatDetailAction.RankingLegendSortTypeReversed) },
                onSort = {
                    onStatDetailAction(
                        StatDetailAction.SortBy(RankingSortType.RankingLegend(it))
                    )
                }
            )
        }
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

    items(
        listOf(
            Pair(
                R.string.rating,
                playerRanking?.rating?.formatted
            ),
            Pair(
                R.string.peakRating,
                playerRanking?.peakRating?.formatted
            ),
            Pair(
                R.string.games,
                playerRanking?.games?.formatted
            ),
            Pair(
                R.string.wins,
                playerRanking?.wins?.formatted
            ),
            Pair(
                R.string.estimatedGlory,
                playerRanking?.estimatedGlory?.formatted
            ),
            Pair(
                R.string.estimatedEloReset,
                playerRanking?.estimatedEloReset?.formatted
            ),
        ),
        key = { it.first }
    ) { (key, value) ->
        CustomRankingField(
            key = key,
            value = value,
            modifier = Modifier.animateItem()
        )
    }
}

private fun LazyGridScope.generalLegendStat(
    legendSortType: StatLegendSortType,
    playerStat: StatDetailUi?,
    columns: Int,
    legendSortTypeReversed: Boolean,
    onStatDetailAction: (StatDetailAction) -> Unit
) {

    if (playerStat?.legends?.isNotEmpty() == true) {
        item(span = { GridItemSpan(columns) }) {
            CustomStatLegendDropDown(legendSortType, legendSortTypeReversed, onStatDetailAction)
        }
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

@Composable
private fun CustomStatLegendDropDown(
    legendSortType: StatLegendSortType,
    legendSortTypeReversed: Boolean,
    onStatDetailAction: (StatDetailAction) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    CustomSortDropDownMenu(
        selected = { Text(stringResource(legendSortType.toStringResource())) },
        icon = Icons.AutoMirrored.Filled.Sort,
        reversed = legendSortTypeReversed,
        expanded = expanded,
        onSortClick = { expanded = !expanded },
        onReversedClick = {
            onStatDetailAction(
                StatDetailAction.StatLegendSortTypeReversed
            )
        }
    ) {
        StatLegendSortType.entries.forEach { sortType ->
            DropdownMenuItem(
                leadingIcon = { Icon(sortType.toIcon(), null) },
                text = {
                    Text(
                        stringResource(
                            sortType.toStringResource()
                        )
                    )
                },
                onClick = {
                    onStatDetailAction(
                        StatDetailAction.SortBy(
                            RankingSortType.StatLegend(
                                sortType
                            )
                        )
                    )
                    expanded = false
                }
            )
        }
    }
}

@Composable
private fun CustomRankedDropDown(
    rankedSortType: GeneralRankingSortType,
    sortTypeReversed: Boolean,
    onReverse: () -> Unit,
    onSort: (GeneralRankingSortType) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    CustomSortDropDownMenu(
        selected = { Text(stringResource(rankedSortType.toStringResource())) },
        icon = Icons.AutoMirrored.Filled.Sort,
        reversed = sortTypeReversed,
        expanded = expanded,
        onSortClick = { expanded = !expanded },
        onReversedClick = onReverse
    ) {
        GeneralRankingSortType.entries.forEach { sortType ->
            DropdownMenuItem(
                leadingIcon = { Icon(sortType.toIcon(), null) },
                text = {
                    Text(
                        stringResource(
                            sortType.toStringResource()
                        )
                    )
                },
                onClick = {
                    onSort(sortType)
                    expanded = false
                }
            )
        }
    }
}


private fun LazyGridScope.generalStat(playerStat: StatDetailUi?) {

    items(
        items = listOf(
            Pair(
                R.string.games,
                playerStat?.games?.formatted
            ),
            Pair(
                R.string.wins,
                playerStat?.wins?.formatted
            ),
            Pair(
                R.string.koBomb,
                playerStat?.koBomb?.formatted
            ),
            Pair(
                R.string.damageBomb,
                playerStat?.damageBomb?.formatted
            ),
            Pair(
                R.string.koMine,
                playerStat?.koMine?.formatted
            ),
            Pair(
                R.string.damageMine,
                playerStat?.damageMine?.formatted
            ),
            Pair(
                R.string.koSpikeball,
                playerStat?.koSpikeball?.formatted
            ),
            Pair(
                R.string.damageSpikeball,
                playerStat?.damageSpikeball?.formatted
            ),
            Pair(
                R.string.koSidekick,
                playerStat?.koSidekick?.formatted
            ),
            Pair(
                R.string.damageSidekick,
                playerStat?.damageSidekick?.formatted
            ),
            Pair(
                R.string.koSnowball,
                playerStat?.koSnowball?.formatted
            ),
            Pair(
                R.string.hitSnowball,
                playerStat?.hitSnowball?.formatted
            ),
        ),
        key = { it.first }
    ) { (key, value) ->
        CustomRankingField(
            key = key,
            value = value,
            modifier = Modifier.animateItem()
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun StatDetailHeader(
    state: StatDetailState,
    onClanSelection: (Long) -> Unit,
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
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp),
                    onClick = {
                        onClanSelection(state.selectedStatDetail.clan.clanId)
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

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            StatType.entries.forEachIndexed { index, statType ->
                ToggleButton(
                    checked = state.selectedStatType == statType,
                    onCheckedChange = {
                        onStatDetailAction(
                            StatDetailAction.SelectStatType(
                                statType
                            )
                        )
                    },
                    shapes = when (index) {
                        0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                        StatFilterType.entries.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                        else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .semantics { role = Role.RadioButton },
                ) {
                    Icon(
                        when (statType) {
                            StatType.Stats -> Icons.Default.QueryStats
                            StatType.Rankings -> Icons.Default.Poll
                        },
                        null
                    )
                    Text(
                        stringResource(
                            when (statType) {
                                StatType.Stats -> R.string.stats
                                StatType.Rankings -> R.string.rankings
                            }
                        )
                    )
                }
            }
        }

        AnimatedContent(
            state.selectedStatType,
        ) { stateType ->
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                when (stateType) {
                    StatType.Stats -> {
                        StatFilterType.entries.forEachIndexed { index, statType ->
                            ToggleButton(
                                checked = state.selectedStatFilterType == statType,
                                onCheckedChange = {
                                    onStatDetailAction(
                                        StatDetailAction.SelectStatFilterType(
                                            statType
                                        )
                                    )
                                },
                                shapes = when (index) {
                                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                    StatFilterType.entries.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics { role = Role.RadioButton },
                            ) {
                                Text(
                                    stringResource(
                                        when (statType) {
                                            StatFilterType.General -> R.string.general
                                            StatFilterType.Legends -> R.string.legends
                                        }
                                    )
                                )
                            }
                        }
                    }

                    StatType.Rankings -> {
                        RankingFilterType.entries.forEachIndexed { index, statType ->
                            ToggleButton(
                                checked = state.selectedRankingFilterType == statType,
                                onCheckedChange = {
                                    onStatDetailAction(
                                        StatDetailAction.SelectRankingFilterType(
                                            statType
                                        )
                                    )
                                },
                                shapes = when (index) {
                                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                    RankingFilterType.entries.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .semantics { role = Role.RadioButton },
                            ) {

                                Text(
                                    stringResource(
                                        when (statType) {
                                            RankingFilterType.Legends -> R.string.legends
                                            RankingFilterType.Teams -> R.string.stats_2v2
                                            RankingFilterType.Stat -> R.string.stats_1v1
                                        }
                                    )
                                )
                            }
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
                    selectedStatType = StatType.Rankings
                ),
                {},
                {},
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
                    selectedStatType = StatType.Rankings
                ),
                {},
                {},
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