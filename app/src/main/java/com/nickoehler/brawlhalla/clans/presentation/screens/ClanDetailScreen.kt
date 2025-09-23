package com.nickoehler.brawlhalla.clans.presentation.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.nickoehler.brawlhalla.clans.domain.ClanDetail
import com.nickoehler.brawlhalla.clans.domain.ClanMember
import com.nickoehler.brawlhalla.clans.domain.ClanRankType
import com.nickoehler.brawlhalla.clans.presentation.ClanAction
import com.nickoehler.brawlhalla.clans.presentation.ClanState
import com.nickoehler.brawlhalla.clans.presentation.components.ClanMemberCard
import com.nickoehler.brawlhalla.clans.presentation.model.ClanSortType
import com.nickoehler.brawlhalla.clans.presentation.model.toClanDetailUi
import com.nickoehler.brawlhalla.clans.presentation.model.toIcon
import com.nickoehler.brawlhalla.clans.presentation.model.toStringResource
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.components.CustomSortDropDownMenu
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.core.presentation.models.toLocalDateTime
import com.nickoehler.brawlhalla.core.presentation.util.ObserveAsEvents
import com.nickoehler.brawlhalla.core.presentation.util.toString
import com.nickoehler.brawlhalla.ranking.presentation.util.toString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClanDetailScreen(
    state: ClanState,
    onBack: () -> Unit,
    onClanAction: (ClanAction) -> Unit,
    modifier: Modifier = Modifier,
    events: Flow<UiEvent> = emptyFlow()
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var screenWidth by remember { mutableStateOf(0.dp) }
    val itemSize = 400.dp
    val columns by remember {
        derivedStateOf {
            screenWidth.div(itemSize).toInt().coerceAtLeast(1)
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

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val clan = state.selectedClan

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
                title = {
                    if (state.isClanDetailLoading) {
                        Box(
                            Modifier
                                .padding(12.dp)
                                .padding(bottom = 4.dp)
                                .height(40.dp)
                                .fillMaxWidth()
                                .clip(CircleShape)
                                .shimmerEffect()
                        )
                    } else if (clan != null) {
                        Text(
                            clan.name,
                            fontSize = 30.sp,
                            lineHeight = 30.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (clan != null) {
                                onClanAction(
                                    ClanAction.ToggleClanFavorites(
                                        clan.id, clan.name
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Favorite,
                            stringResource(R.string.favorites),
                            tint = if (state.isClanDetailFavorite)
                                MaterialTheme.colorScheme.primary else
                                MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.scaffoldWindowInsets)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (state.isClanDetailLoading) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp),
                    columns = GridCells.Fixed(columns),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item(span = { GridItemSpan(columns) }) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                Modifier
                                    .size(150.dp, 25.dp)
                                    .clip(CircleShape)
                                    .shimmerEffect()
                            )
                            Box(
                                Modifier
                                    .size(350.dp, 25.dp)
                                    .clip(CircleShape)
                                    .shimmerEffect()
                            )
                        }
                    }
                    item(span = { GridItemSpan(columns) }) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                Modifier
                                    .size(130.dp, 50.dp)
                                    .clip(CircleShape)
                                    .shimmerEffect()
                            )
                            Box(
                                Modifier
                                    .size(50.dp, 50.dp)
                                    .clip(CircleShape)
                                    .shimmerEffect()
                            )
                        }
                    }
                    items(20) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .clip(CircleShape)
                                .shimmerEffect()
                        )
                    }
                }

            } else if (clan != null) {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp),
                    columns = GridCells.Fixed(columns),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item(span = { GridItemSpan(columns) }) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("XP ${clan.xp.formatted}")
                            Text(stringResource(R.string.createDate, clan.createDate.formatted))
                        }
                    }
                    item(span = { GridItemSpan(columns) }) {
                        var expanded by remember { mutableStateOf(false) }
                        CustomSortDropDownMenu(
                            reversed = state.reversedSortType,
                            expanded = expanded,
                            icon = Icons.AutoMirrored.Filled.Sort,
                            onSortClick = {
                                expanded = !expanded
                            },
                            onReversedClick = {
                                onClanAction(ClanAction.ReverseSortType)
                            },
                            selected = {
                                Text(stringResource(state.sortType.toStringResource()))
                            },
                        ) {
                            ClanSortType.entries.forEach { sort ->
                                DropdownMenuItem(
                                    leadingIcon = { Icon(sort.toIcon(), null) },
                                    text = { Text(stringResource(sort.toStringResource())) },
                                    onClick = {
                                        expanded = false
                                        onClanAction(ClanAction.SelectSortType(sort))
                                    }
                                )
                            }
                        }
                    }
                    items(clan.members, { it.brawlhallaId }) { member ->
                        ClanMemberCard(
                            member,
                            onClick = { id ->
                                onClanAction(
                                    ClanAction.SelectMember(id)
                                )
                            },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ClanDetailScreenPreviewLoaded() {
    BrawlhallaTheme {
        Surface {
            ClanDetailScreen(
                state = ClanState(
                    selectedClan = clanDetailSample.toClanDetailUi()
                ),
                {},
                {}
            )
        }
    }
}

@Preview
@Composable
private fun ClanDetailScreenPreviewLoading() {
    BrawlhallaTheme {
        Surface {
            ClanDetailScreen(
                state = ClanState(
                    isClanDetailLoading = true,
                ),
                {},
                {}
            )
        }
    }
}

internal val clanDetailSample =
    ClanDetail(
        1,
        "Blue Mammoth Games",
        1464206400L.toLocalDateTime(),
        86962,
        listOf(
            ClanMember(
                3,
                "[BMG] Chill Penguin X",
                ClanRankType.Leader,
                1464206400L.toLocalDateTime(),
                6664
            ),
            ClanMember(
                2,
                "bmg | dan",
                ClanRankType.Officer,
                1464221047L.toLocalDateTime(),
                4492
            )
        )
    )
