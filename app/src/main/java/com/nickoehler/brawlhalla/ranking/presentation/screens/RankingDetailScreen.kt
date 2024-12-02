package com.nickoehler.brawlhalla.ranking.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.ranking.domain.StatClan
import com.nickoehler.brawlhalla.ranking.domain.StatDetail
import com.nickoehler.brawlhalla.ranking.domain.StatLegend
import com.nickoehler.brawlhalla.ranking.presentation.RankingState
import com.nickoehler.brawlhalla.ranking.presentation.models.toStatDetailUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun RankingDetailScreen(
    state: RankingState,
    modifier: Modifier = Modifier
) {
    val player = state.selectedRanking
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        if (state.isDetailLoading) {
            CircularProgressIndicator()
        } else if (player != null) {
            Column {
                Text(player.level.toString())
                Text(
                    player.name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun RankingDetailScreenPreview() {
    BrawlhallaTheme {
        Surface {
            RankingDetailScreen(
                state = RankingState(
                    selectedRanking = statDetailSample.toStatDetailUi()
                )
            )
        }
    }
}


internal val statDetailSample = StatDetail(
    brawlhallaId = 2316541,
    name = "NicKoehler",
    xp = 4261524,
    level = 100,
    xpPercentage = 0f,
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
            540349,
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
            0.6792734706714892
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
            135574,
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
            92623,
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
            99789,
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

