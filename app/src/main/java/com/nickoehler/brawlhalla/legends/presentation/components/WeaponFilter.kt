package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.components.WeaponChip
import com.nickoehler.brawlhalla.core.presentation.domain.toWeaponUi
import com.nickoehler.brawlhalla.legends.presentation.LegendsListState
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme


@Composable
fun WeaponsFilter(
    state: LegendsListState,
    onWeaponAction: (WeaponAction) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        state.openFilters,
        modifier = modifier,
        label = "openFilters"
    ) {
        if (it) {
            LazyHorizontalStaggeredGrid(
                modifier = Modifier.Companion
                    .fillMaxWidth()
                    .heightIn(min = 40.dp, max = 100.dp),
                rows = StaggeredGridCells.Adaptive(30.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalItemSpacing = 4.dp,
            ) {
                items(state.weapons) { weapon ->
                    WeaponChip(weapon, onWeaponAction)
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun WeaponsFilterPreview() {
    BrawlhallaTheme {
        Surface {
            WeaponsFilter(
                state = LegendsListState(
                    openFilters = true,
                    weapons = (0..30).map { "hammer".toWeaponUi() }
                ),
                {}
            )
        }
    }
}