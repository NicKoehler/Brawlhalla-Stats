package com.nickoehler.brawlhalla.favorites.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    players: List<Player>? = emptyList(),
    clans: List<Clan>? = emptyList(),
    onFavoriteAction: (FavoriteAction) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {

        if (players.isNullOrEmpty() && clans.isNullOrEmpty()) {
            Text(
                stringResource(R.string.favoritesHint),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        if (!players.isNullOrEmpty()) {
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.favoritePlayers))
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item { Spacer(Modifier) }
                    items(players, {it.id}) { player ->
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
            }
        }

        if (!clans.isNullOrEmpty()) {
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.favoriteClans))
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item { Spacer(Modifier) }
                    items(clans,  {it.id}) { clan ->
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
                players = (1..100).map
                { Player(it, name = "Nic") },
                clans = (1..3).map
                { Clan(it, name = "Nic") }
            )
        }
    }
}