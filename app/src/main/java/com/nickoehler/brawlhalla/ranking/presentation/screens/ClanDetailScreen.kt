package com.nickoehler.brawlhalla.ranking.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ranking.domain.ClanDetail
import com.nickoehler.brawlhalla.ranking.domain.ClanMember
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.RankingState
import com.nickoehler.brawlhalla.ranking.presentation.components.ZonedDateTimeDisplay
import com.nickoehler.brawlhalla.ranking.presentation.models.toClanDetailUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import java.time.LocalDateTime
import java.time.ZoneOffset

@Composable
fun ClanDetailScreen(
    state: RankingState,
    onRankingAction: (RankingAction) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val clan = state.selectedClan
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(Modifier)
        if (state.isStatDetailLoading) {
            CircularProgressIndicator()
        } else if (clan != null) {
            Text(
                clan.name,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                lineHeight = 40.sp,
                textAlign = TextAlign.Center
            )

            Text("XP ${clan.xp.formatted}")
            ZonedDateTimeDisplay(clan.createDate)



            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(clan.members) { member ->
                    Column {
                        CustomCard(
                            modifier = Modifier.fillParentMaxWidth(),
                            onClick = {
                                onRankingAction(RankingAction.SelectRanking(member.brawlhallaId))
                            }
                        ) {
                            Text(member.name, modifier = Modifier.weight(1f))

                            Column (horizontalAlignment = Alignment.End) {
                                Text(member.rank)
                                ZonedDateTimeDisplay(member.joinDate)
                            }

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
                state = RankingState(
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
