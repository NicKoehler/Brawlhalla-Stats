package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.models.BracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toBracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun RankingCard(
    modifier: Modifier = Modifier,
    ranking: RankingUi? = null,
    selectedBracket: BracketUi = Bracket.ONE_VS_ONE.toBracketUi(),
    onRankingAction: (RankingAction) -> Unit = {}
) {
    when (ranking) {
        is RankingUi.RankingSoloUi -> RankingSoloCard(
            ranking = ranking,
            onRankingAction = onRankingAction
        )

        is RankingUi.RankingTeamUi -> RankingTeamCard(
            ranking,
            onRankingAction = onRankingAction
        )

        else -> {
            when (selectedBracket.value) {
                Bracket.ONE_VS_ONE, Bracket.TWO_VS_TWO -> RankingSoloCard(
                    modifier = modifier,
                )

                Bracket.ROTATING -> RankingTeamCard(
                    modifier = modifier,
                )
            }
        }
    }

}


@PreviewLightDark
@Composable
private fun RankingCardPreview() {
    BrawlhallaTheme {
        Surface {
            Column {
                RankingCard(
                    modifier = Modifier.fillMaxWidth(),
                    ranking = rankingSoloSample.toRankingUi(),
                    selectedBracket = Bracket.ONE_VS_ONE.toBracketUi()
                )

                RankingCard(
                    modifier = Modifier.fillMaxWidth(),
                    ranking = rankingTeamSample.toRankingUi(),
                    selectedBracket = Bracket.ONE_VS_ONE.toBracketUi()
                )
            }
        }
    }
}

