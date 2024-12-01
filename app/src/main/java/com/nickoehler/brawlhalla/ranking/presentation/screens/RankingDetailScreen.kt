package com.nickoehler.brawlhalla.ranking.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nickoehler.brawlhalla.ranking.presentation.RankingState

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
            Text(player.name)
        }
    }
}