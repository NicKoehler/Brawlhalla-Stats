package com.nickoehler.brawlhalla.guilds.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.material3.scrollbar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.UiEvent
import com.nickoehler.brawlhalla.core.presentation.components.CustomSortDropDownMenu
import com.nickoehler.brawlhalla.core.presentation.components.ShowError
import com.nickoehler.brawlhalla.core.presentation.components.shimmerEffect
import com.nickoehler.brawlhalla.core.presentation.models.toLocalDateTime
import com.nickoehler.brawlhalla.core.presentation.util.ObserveAsEvents
import com.nickoehler.brawlhalla.guilds.domain.GuildDetail
import com.nickoehler.brawlhalla.guilds.domain.GuildMember
import com.nickoehler.brawlhalla.guilds.domain.GuildRankType
import com.nickoehler.brawlhalla.guilds.presentation.GuildAction
import com.nickoehler.brawlhalla.guilds.presentation.GuildState
import com.nickoehler.brawlhalla.guilds.presentation.components.GuildMemberCard
import com.nickoehler.brawlhalla.guilds.presentation.model.GuildSortType
import com.nickoehler.brawlhalla.guilds.presentation.model.toGuildDetailUi
import com.nickoehler.brawlhalla.guilds.presentation.model.toIcon
import com.nickoehler.brawlhalla.guilds.presentation.model.toStringResource
import com.nickoehler.brawlhalla.ranking.presentation.util.toString
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.Spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuildDetailScreen(
    state: GuildState,
    onBack: () -> Unit,
    onGuildAction: (GuildAction) -> Unit,
    modifier: Modifier = Modifier,
    events: Flow<UiEvent> = emptyFlow()
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val haptic = LocalHapticFeedback.current
    var screenWidth by remember { mutableStateOf(0.dp) }
    val itemSize = 400.dp
    val columns by remember {
        derivedStateOf {
            screenWidth.div(itemSize).toInt().coerceAtLeast(1)
        }
    }

    val scrollState = rememberLazyGridState()

    ObserveAsEvents(events) { event ->
        when (event) {
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

    val guild = state.selectedGuild

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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    Box(modifier = Modifier.padding(start = 8.dp)) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                stringResource(R.string.back),
                            )
                        }
                    }
                },
                title = {
                    if (state.isGuildDetailLoading) {
                        Box(
                            Modifier
                                .padding(12.dp)
                                .padding(bottom = 4.dp)
                                .height(40.dp)
                                .fillMaxWidth()
                                .clip(CircleShape)
                                .shimmerEffect()
                        )
                    } else if (guild != null) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 12.dp)
                        ) {
                            Text(
                                guild.name,
                                fontSize = 32.sp,
                                lineHeight = 36.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                stringResource(R.string.members, state.selectedGuild.members.size),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                actions = {
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        IconButton(
                            onClick = {
                                if (guild != null) {
                                    onGuildAction(
                                        GuildAction.ToggleGuildFavorites(
                                            guild.id, guild.name
                                        )
                                    )
                                    haptic.performHapticFeedback(HapticFeedbackType.KeyboardTap)
                                }
                            },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                        ) {
                            Icon(
                                Icons.Default.Favorite,
                                stringResource(R.string.favorites),
                                tint = if (state.isGuildDetailFavorite)
                                    MaterialTheme.colorScheme.primary else
                                    MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        AnimatedContent(state.error) { error ->
            when (error) {
                null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues)
                            .padding(horizontal = Spacing.scaffoldWindowInsets - 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (state.isGuildDetailLoading) {
                            LazyVerticalGrid(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(16.dp),
                                columns = GridCells.Fixed(columns),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
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

                        } else if (guild != null) {
                            LazyVerticalGrid(
                                state = scrollState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .scrollbar(
                                        scrollState.scrollIndicatorState,
                                        orientation = Orientation.Vertical
                                    ),
                                contentPadding = PaddingValues(16.dp),
                                columns = GridCells.Fixed(columns),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                item(span = { GridItemSpan(columns) }) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            "XP ${guild.xp.formatted}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            stringResource(
                                                R.string.createDate,
                                                guild.createDate.formatted
                                            ),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
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
                                            onGuildAction(GuildAction.ReverseSortType)
                                        },
                                        selected = {
                                            Text(stringResource(state.sortType.toStringResource()))
                                        },
                                    ) {
                                        GuildSortType.entries.forEach { sort ->
                                            DropdownMenuItem(
                                                leadingIcon = { Icon(sort.toIcon(), null) },
                                                text = { Text(stringResource(sort.toStringResource())) },
                                                onClick = {
                                                    expanded = false
                                                    onGuildAction(GuildAction.SelectSortType(sort))
                                                }
                                            )
                                        }
                                    }
                                }
                                items(guild.members, { it.brawlhallaId }) { member ->
                                    GuildMemberCard(
                                        member,
                                        onClick = { id ->
                                            onGuildAction(
                                                GuildAction.SelectMember(id)
                                            )
                                        },
                                        modifier = Modifier.animateItem()
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {
                    ShowError(error, { onGuildAction(GuildAction.Reload) })
                }
            }
        }
    }
}

@Preview
@Composable
private fun GuildDetailScreenPreviewLoaded() {
    BrawlhallaTheme {
        Surface {
            GuildDetailScreen(
                state = GuildState(
                    selectedGuild = guildDetailSample.toGuildDetailUi()
                ),
                {},
                {}
            )
        }
    }
}

@Preview
@Composable
private fun GuildDetailScreenPreviewLoading() {
    BrawlhallaTheme {
        Surface {
            GuildDetailScreen(
                state = GuildState(
                    isGuildDetailLoading = true,
                ),
                {},
                {}
            )
        }
    }
}

internal val guildDetailSample =
    GuildDetail(
        1,
        "Blue Mammoth Games",
        1464206400L.toLocalDateTime(),
        86962,
        4620759,
        "Meets every Thursday!",
        listOf(
            "Social",
            "Friendly",
            "NightOwls"
        ),
        "alwaysbecreasing",
        114953,
        6184,
        true,
        listOf(
            GuildMember(
                3,
                "[BMG] Chill Penguin X",
                GuildRankType.Leader,
                1464206400L.toLocalDateTime(),
                6664,
                6664,
            ),
            GuildMember(
                2,
                "bmg | dan",
                GuildRankType.Officer,
                1464221047L.toLocalDateTime(),
                4492,
                4492,
            )
        )
    )
