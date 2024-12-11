package com.nickoehler.brawlhalla.favorites.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.favorites.FavoriteAction
import com.nickoehler.brawlhalla.favorites.presentation.FavoritesState
import com.nickoehler.brawlhalla.favorites.presentation.model.FavoriteType
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    state: FavoritesState = FavoritesState(),
    onFavoriteAction: (FavoriteAction) -> Unit = {}
) {
    if (state.players.isEmpty() && state.clans.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
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
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val players = state.players
            val clans = state.clans
            SingleChoiceSegmentedButtonRow {
                SegmentedButton(
                    state.selectedFavoriteType == FavoriteType.PLAYERS,
                    { onFavoriteAction(FavoriteAction.SelectFavorite(FavoriteType.PLAYERS)) },
                    enabled = state.players.isNotEmpty(),
                    shape = SegmentedButtonDefaults.itemShape(0, 2),
                ) {
                    Text(stringResource(R.string.players))
                }

                SegmentedButton(
                    state.selectedFavoriteType == FavoriteType.CLANS,
                    { onFavoriteAction(FavoriteAction.SelectFavorite(FavoriteType.CLANS)) },
                    enabled = state.clans.isNotEmpty(),
                    shape = SegmentedButtonDefaults.itemShape(1, 2)
                ) {
                    Text(stringResource(R.string.clans))
                }
            }
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (state.selectedFavoriteType == FavoriteType.PLAYERS) {
                    item { Spacer(Modifier) }
                    items(players, { it.id }) { player ->
                        CustomCard(
                            onClick = {
                                onFavoriteAction(
                                    FavoriteAction.SelectPlayer(player.id)
                                )
                            }
                        ) {
                            Text(player.name, modifier.weight(1f))
                        }
                    }
                    item { Spacer(Modifier) }
                }

                if (state.selectedFavoriteType == FavoriteType.CLANS) {
                    item { Spacer(Modifier) }
                    items(clans, { it.id }) { clan ->
                        CustomCard(
                            onClick = {
                                onFavoriteAction(
                                    FavoriteAction.SelectClan(clan.id)
                                )
                            }
                        ) {
                            Text(clan.name, modifier.weight(1f))
                        }
                    }
                    item { Spacer(Modifier) }
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