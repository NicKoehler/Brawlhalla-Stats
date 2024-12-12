package com.nickoehler.brawlhalla.favorites.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import com.nickoehler.brawlhalla.core.presentation.components.CustomTopAppBar
import com.nickoehler.brawlhalla.favorites.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.FavoritesState
import com.nickoehler.brawlhalla.favorites.presentation.components.FavoritesItem
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    state: FavoritesState = FavoritesState(),
    onFavoriteAction: (FavoriteAction) -> Unit = {}
) {
    Scaffold(
        modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(
                stringResource(R.string.favorites),
                showSearch = false,
            )
        }
    ) {
        if (state.players.isEmpty() && state.clans.isEmpty()) {
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
        } else {
            val coroutineScope = rememberCoroutineScope()
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val players = state.players
                val clans = state.clans
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(
                        state.selectedFavoriteType == FavoriteType.PLAYERS,
                        { onFavoriteAction(FavoriteAction.SelectFavorite(FavoriteType.PLAYERS)) },
                        icon = {
                            SegmentedButtonDefaults.Icon(
                                activeContent = {
                                    Icon(
                                        Icons.Default.Person,
                                        null
                                    )
                                },
                                active = state.selectedFavoriteType == FavoriteType.PLAYERS
                            )
                        },
                        enabled = state.players.isNotEmpty(),
                        shape = SegmentedButtonDefaults.itemShape(0, 2),
                    ) {
                        Text(stringResource(R.string.players))
                    }

                    SegmentedButton(
                        state.selectedFavoriteType == FavoriteType.CLANS,
                        { onFavoriteAction(FavoriteAction.SelectFavorite(FavoriteType.CLANS)) },
                        icon = {
                            SegmentedButtonDefaults.Icon(
                                activeContent = {
                                    Icon(
                                        Icons.Default.People,
                                        null
                                    )
                                },
                                active = state.selectedFavoriteType == FavoriteType.CLANS
                            )
                        },
                        enabled = state.clans.isNotEmpty(),
                        shape = SegmentedButtonDefaults.itemShape(1, 2)
                    ) {
                        Text(stringResource(R.string.clans))
                    }
                }
                AnimatedContent(
                    state.selectedFavoriteType,
                    label = "selectedFavoriteType"
                ) { type ->
                    LazyColumn(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        item { Spacer(Modifier) }
                        when (type) {
                            FavoriteType.PLAYERS ->
                                items(players, { player -> player.id }) { player ->
                                    FavoritesItem(
                                        coroutineScope,
                                        player.name,
                                        stringResource(R.string.areYouSureDeletePlayerTitle),
                                        stringResource(R.string.areYouSureDeletePlayerDesc),
                                        Icons.Default.Person,
                                        {
                                            onFavoriteAction(
                                                FavoriteAction.DeletePlayer(player.id)
                                            )
                                        },
                                        {
                                            onFavoriteAction(
                                                FavoriteAction.SelectPlayer(player.id)
                                            )
                                        },
                                        Modifier
                                            .fillParentMaxWidth()
                                            .animateItem()
                                    )
                                }

                            FavoriteType.CLANS ->
                                items(clans, { clan -> clan.id }) { clan ->
                                    FavoritesItem(
                                        coroutineScope,
                                        clan.name,
                                        stringResource(R.string.areYouSureDeleteClanTitle),
                                        stringResource(R.string.areYouSureDeleteClanDesc),
                                        Icons.Default.People,
                                        {
                                            onFavoriteAction(
                                                FavoriteAction.DeleteClan(clan.id)
                                            )
                                        },
                                        {
                                            onFavoriteAction(
                                                FavoriteAction.SelectClan(clan.id)
                                            )
                                        },
                                        Modifier
                                            .fillParentMaxWidth()
                                            .animateItem()
                                    )
                                }

                            else -> {}
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
                    players = (1..100).map
                    { Player(it, name = "Nic") },
                    clans = (1..3).map
                    { Clan(it, name = "Nic") }
                )
            )
        }
    }
}