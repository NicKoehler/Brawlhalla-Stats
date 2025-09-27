package com.nickoehler.brawlhalla.ranking.presentation.components.ranking_card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.nickoehler.brawlhalla.ranking.domain.Bracket
import com.nickoehler.brawlhalla.ranking.presentation.RankingAction
import com.nickoehler.brawlhalla.ranking.presentation.models.BracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toBracketUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toRankingUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.delay

@Composable
fun RankingCard(
    modifier: Modifier = Modifier,
    ranking: RankingUi? = null,
    selectedBracket: BracketUi = Bracket.ONE_VS_ONE.toBracketUi(),
    onRankingAction: (RankingAction) -> Unit = {}
) {

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100L)
        visible = true
    }

    val animatedFloat by animateFloatAsState(if (visible) 1f else 0.9f)


    when (ranking) {
        is RankingUi.RankingSoloUi -> RankingSoloCard(
            modifier = modifier
                .scale(animatedFloat)
                .alpha(animatedFloat),
            ranking = ranking,
            onRankingAction = onRankingAction
        )

        is RankingUi.RankingTeamUi -> RankingTeamCard(
            modifier = modifier
                .scale(animatedFloat)
                .alpha(animatedFloat),
            ranking = ranking,
            onRankingAction = onRankingAction
        )

        else -> {
            when (selectedBracket.value) {
                Bracket.ONE_VS_ONE, Bracket.TWO_VS_TWO -> RankingSoloCard(
                    modifier = modifier
                        .scale(animatedFloat)
                        .alpha(animatedFloat),
                )

                Bracket.ROTATING -> RankingTeamCard(
                    modifier = modifier
                        .scale(animatedFloat)
                        .alpha(animatedFloat),
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

