package com.nickoehler.brawlhalla.ranking.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.components.CustomCard
import com.nickoehler.brawlhalla.ranking.presentation.models.RankingUi
import com.nickoehler.brawlhalla.ranking.presentation.models.toLocalizedString
import com.nickoehler.brawlhalla.ranking.presentation.models.toStatLegendUi
import com.nickoehler.brawlhalla.ranking.presentation.screens.statDetailSample
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun TeamStatItem(
    team: RankingUi.RankingTeamUi,
    modifier: Modifier = Modifier,
    onStatDetailAction: () -> Unit
) {
    val context = LocalContext.current
    var isExpanded by remember {
        mutableStateOf(false)
    }
    CustomCard(
        onClick = { isExpanded = !isExpanded },
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedContent(isExpanded, label = team.teamName) { isOpen ->
            if (isOpen) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.animateContentSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            team.region.flag,
                            fontSize = 32.sp

                        )
                        Text(
                            team.teamName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(Modifier.height(20.dp))
                    CustomLegendField(R.string.rating, team.rating.formatted)
                    CustomLegendField(R.string.tier, team.tier.toLocalizedString(context = context))
                    CustomLegendField(R.string.games, team.games.formatted)
                    CustomLegendField(R.string.wins, team.wins.formatted)
                    CustomLegendField(R.string.winRate, team.winRate.formatted)
                    CustomLegendField(R.string.losses, team.losses.formatted)
                    CustomLegendField(R.string.peakRating, team.peakRating.formatted)

                    Button(
                        onClick = onStatDetailAction
                    ) {
                        Text(stringResource(R.string.goToTeamMate))
                    }
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.animateContentSize()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(team.teamName)
                        }
                    }

                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LegendStatItemPreview() {

    BrawlhallaTheme {
        Surface {
            LegendStatItem(
                statDetailSample.legends[0].toStatLegendUi()
            ) { }
        }
    }

}