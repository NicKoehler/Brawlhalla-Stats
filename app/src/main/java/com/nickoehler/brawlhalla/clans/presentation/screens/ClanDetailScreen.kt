package com.nickoehler.brawlhalla.clans.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.clans.domain.ClanDetail
import com.nickoehler.brawlhalla.clans.domain.ClanMember
import com.nickoehler.brawlhalla.clans.presentation.ClanAction
import com.nickoehler.brawlhalla.clans.presentation.ClanState
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ranking.presentation.components.ZonedDateTimeDisplay
import com.nickoehler.brawlhalla.ranking.presentation.models.toClanDetailUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun ClanDetailScreen(
    clanId: Int? = null,
    state: ClanState,
    onClanAction: (ClanAction) -> Unit = {},
    modifier: Modifier = Modifier
) {

    val clan = state.selectedClan

    LaunchedEffect(Unit) {
        if (clanId != null) {
            onClanAction(ClanAction.SelectClan(clanId))
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(Modifier)
        if (state.isClanDetailLoading) {
            CircularProgressIndicator()
        } else if (clan != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(40.dp))
                Text(
                    clan.name,
                    modifier.weight(1f),
                    fontSize = 30.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                IconButton(
                    onClick = {
                        onClanAction(
                            ClanAction.ToggleClanFavorites(clan.id, clan.name)
                        )
                    }
                ) {
                    Icon(
                        Icons.Default.Star,
                        null,
                        tint = if (state.isClanDetailFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            Text("XP ${clan.xp.formatted}")
            ZonedDateTimeDisplay(clan.createDate)

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(clan.members) { member ->
                    CustomCard(
                        modifier = Modifier.fillParentMaxWidth(),
                        onClick = {
                            onClanAction(ClanAction.SelectMember(member.brawlhallaId))
                        }
                    ) {
                        Text(member.name, modifier = Modifier.weight(1f))

                        Column(horizontalAlignment = Alignment.End) {
                            Text(member.rank)
                            ZonedDateTimeDisplay(member.joinDate)
                        }

                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ClanDetailScreenPreview() {
    BrawlhallaTheme {
        Surface {
            ClanDetailScreen(
                state = ClanState(
                    selectedClan = clanDetailSample.toClanDetailUi()
                )
            )
        }
    }
}

internal val clanDetailSample =
    ClanDetail(
        1,
        "Blue Mammoth Games",
        LocalDateTime.ofEpochSecond(1464206400, 0, ZoneOffset.UTC),
        86962,
        listOf(
            ClanMember(
                3,
                "[BMG] Chill Penguin X",
                "Leader",
                LocalDateTime.ofEpochSecond(1464206400, 0, ZoneOffset.UTC),
                6664
            ),
            ClanMember(
                2,
                "bmg | dan",
                "Officer",
                LocalDateTime.ofEpochSecond(1464221047, 0, ZoneOffset.UTC),

                4492
            )
        )
    )
