package com.nickoehler.brawlhalla.legends.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.core.presentation.AppBarAction
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.components.CustomTopAppBar
import com.nickoehler.brawlhalla.legends.presentation.LegendAction
import com.nickoehler.brawlhalla.legends.presentation.LegendsListState
import com.nickoehler.brawlhalla.legends.presentation.components.LazyLegendsCards
import com.nickoehler.brawlhalla.legends.presentation.components.legendSample
import com.nickoehler.brawlhalla.legends.presentation.models.toLegendUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LegendListScreen(
    state: LegendsListState,
    onLegendAction: (LegendAction) -> Unit,
    onWeaponAction: (WeaponAction) -> Unit,
    onAppBarAction: (AppBarAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {

        val scrollBehavior =
            TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    title = stringResource(R.string.legends),
                    state = state.appBarState,
                    onAppBarAction = onAppBarAction,
                    scrollBehavior = scrollBehavior,
                ) {
                    IconButton(
                        onClick = { onLegendAction(LegendAction.ToggleFilters) },
                        colors = if (state.openFilters) {
                            IconButtonDefaults.iconButtonColors().copy(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            IconButtonDefaults.iconButtonColors()
                        }
                    ) {
                        Icon(
                            Icons.Default.FilterAlt,
                            null,
                        )
                    }
                }
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
                onWeaponAction = {},
                onAppBarAction = {}
            )
        }
    }
}

