package com.nickoehler.brawlhalla.legends.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsListState
import com.nickoehler.brawlhalla.legends.presentation.components.LazyLegendsCards
import com.nickoehler.brawlhalla.legends.presentation.components.LegendsTopBar
import com.nickoehler.brawlhalla.legends.presentation.components.legendSample
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegendListScreen(
    state: LegendsListState,
    onLegendAction: (LegendAction) -> Unit,
    onWeaponAction: (WeaponAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        if (state.isListLoading) {
            CircularProgressIndicator()
        } else {
            Scaffold(
                topBar = {
                    LegendsTopBar(
                        state,
                        onLegendAction,
                        scrollBehavior
                    )
                },
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                LazyLegendsCards(
                    state,
                    onLegendAction,
                    onWeaponAction,
                    modifier = modifier.padding(it),
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun LegendListScreenPreview() {
    BrawlhallaTheme {
        Surface {
            LegendListScreen(
                LegendsListState(
                    legends = (0..100).map {
                        legendSample.copy(legendId = it).toLegendUi()
                    },
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                onLegendAction = {},
                onWeaponAction = {}
            )
        }
    }
}

