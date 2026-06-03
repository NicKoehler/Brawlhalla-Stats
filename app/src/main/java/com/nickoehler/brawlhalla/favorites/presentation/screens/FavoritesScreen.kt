package com.nickoehler.brawlhalla.favorites.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.presentation.components.draggableItems
import com.nickoehler.brawlhalla.core.presentation.components.rememberDraggableListState
import com.nickoehler.brawlhalla.favorites.presentation.components.FavoritesItem
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoritesState
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import com.nickoehler.brawlhalla.ui.theme.Spacing


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FavoritesScreen(
    state: FavoritesState,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onInfoSelection: () -> Unit = {},
    onFavoriteAction: (FavoriteAction) -> Unit = {}
) {

    val haptic = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()
    val dismissPlayersStateMap = remember { mutableStateMapOf<Long, SwipeToDismissBoxState>() }
    val dismissGuildsStateMap = remember { mutableStateMapOf<Long, SwipeToDismissBoxState>() }

    val draggablePlayersState = rememberDraggableListState(
        onMove = { fromIndex, toIndex ->
            onFavoriteAction(FavoriteAction.PlayerDragged(fromIndex, toIndex))
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        },
        onMoveCompleted = {
            onFavoriteAction(FavoriteAction.PersistPlayers)
        },
    )

    val draggableGuildsState = rememberDraggableListState(
        onMove = { fromIndex, toIndex ->
            onFavoriteAction(FavoriteAction.GuildDragged(fromIndex, toIndex))
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        },
        onMoveCompleted = {
            onFavoriteAction(FavoriteAction.PersistGuilds)
        },
    )
    val players = state.players
    val guilds = state.guilds

    Scaffold(
        modifier
            .fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        },
        topBar = {
            TopAppBar(
                { Text(stringResource(R.string.favorites), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onInfoSelection) {
                        Icon(Icons.Default.Settings, stringResource(R.string.settings_title))
                    }
                }
            )
        },
    ) {
        if (state.players.isEmpty() && state.guilds.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.favoritesHint),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FlowRow(
                modifier = Modifier.padding(horizontal = Spacing.scaffoldWindowInsets),
                horizontalArrangement = if (players.isEmpty() && guilds.isEmpty()) {
                    Arrangement.spacedBy(8.dp)
                } else {
                    Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween)
                },
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                ToggleButton(
                    checked = state.selectedFavoriteType == FavoriteType.Players && players.isNotEmpty(),
                    onCheckedChange = { onFavoriteAction(FavoriteAction.SelectFavorite(FavoriteType.Players)) },
                    shapes = if (players.isEmpty() && guilds.isEmpty()) {
                        ToggleButtonDefaults.shapes()
                    } else {
                        ButtonGroupDefaults.connectedLeadingButtonShapes()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .semantics { role = Role.RadioButton },
                    enabled = state.players.isNotEmpty(),
                ) {
                    Icon(
                        Icons.Default.Person,
                        stringResource(R.string.players)
                    )
                    Text(stringResource(R.string.players))
                }

                ToggleButton(
                    checked = state.selectedFavoriteType == FavoriteType.Guilds && guilds.isNotEmpty(),
                    onCheckedChange = { onFavoriteAction(FavoriteAction.SelectFavorite(FavoriteType.Guilds)) },
                    shapes = if (players.isEmpty() && guilds.isEmpty()) {
                        ToggleButtonDefaults.shapes()
                    } else {
                        ButtonGroupDefaults.connectedTrailingButtonShapes()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .semantics { role = Role.RadioButton },
                    enabled = state.guilds.isNotEmpty(),
                ) {
                    Icon(
                        Icons.Default.People,
                        stringResource(R.string.guilds)
                    )
                    Text(stringResource(R.string.guilds))
                }
            }
            Spacer(Modifier.height(10.dp))
            if (state.selectedFavoriteType != null) {
                LazyColumn(
                    state = when (state.selectedFavoriteType) {
                        FavoriteType.Players -> draggablePlayersState.listState
                        FavoriteType.Guilds -> draggableGuildsState.listState
                    },
                    modifier = Modifier
                        .padding(horizontal = Spacing.scaffoldWindowInsets)
                        .fillMaxHeight(),
                    contentPadding = PaddingValues(bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    when (state.selectedFavoriteType) {
                        FavoriteType.Players ->
                            draggableItems(
                                draggablePlayersState,
                                players,
                                { player -> player.id }) { player, isDragging ->
                                val dismissState =
                                    dismissPlayersStateMap.getOrPut(player.id) { rememberSwipeToDismissBoxState() }
                                FavoritesItem(
                                    player.id,
                                    player.name,
                                    Icons.Default.Person,
                                    coroutineScope,
                                    snackBarHostState,
                                    dismissState,
                                    draggablePlayersState,
                                    {
                                        onFavoriteAction(
                                            FavoriteAction.DeletePlayer(player.id)
                                        )
                                        haptic.performHapticFeedback(HapticFeedbackType.Reject)
                                    },
                                    {
                                        onFavoriteAction(
                                            FavoriteAction.RestorePlayer(player)
                                        )
                                    },
                                    {
                                        onFavoriteAction(
                                            FavoriteAction.PlayerClicked(player.id)
                                        )
                                    },
                                    Modifier
                                        .animateItem()
                                        .fillParentMaxWidth()
                                )
                            }

                        FavoriteType.Guilds ->
                            draggableItems(
                                draggableGuildsState,
                                guilds,
                                { guild -> guild.id }
                            ) { guild, isDragging ->
                                val dismissState =
                                    dismissGuildsStateMap.getOrPut(guild.id) { rememberSwipeToDismissBoxState() }
                                FavoritesItem(
                                    guild.id,
                                    guild.name,
                                    Icons.Default.People,
                                    coroutineScope,
                                    snackBarHostState,
                                    dismissState,
                                    draggableGuildsState,
                                    {
                                        onFavoriteAction(
                                            FavoriteAction.DeleteGuild(guild.id)
                                        )
                                        haptic.performHapticFeedback(HapticFeedbackType.Reject)
                                    },
                                    {
                                        onFavoriteAction(
                                            FavoriteAction.RestoreGuild(guild)
                                        )
                                    },
                                    {
                                        onFavoriteAction(
                                            FavoriteAction.GuildClicked(guild.id)
                                        )
                                    },
                                    Modifier
                                        .animateItem()
                                        .fillParentMaxWidth()
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
private fun FavoritesScreenPreview() {
    BrawlhallaTheme {
        Surface {
            FavoritesScreen(
                state = FavoritesState(
                    players = (1L..100L).map
                    { Player(it, name = "Nic", 0) },
                    guilds = (1L..3L).map
                    { Guild(it, name = "Nic", 0) },
                    selectedFavoriteType = FavoriteType.Players
                ),
                SnackbarHostState()
            )
        }
    }
}