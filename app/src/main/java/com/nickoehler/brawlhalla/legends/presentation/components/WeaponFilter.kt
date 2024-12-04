package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.components.WeaponChip
import com.nickoehler.brawlhalla.core.presentation.models.WeaponUi
import com.nickoehler.brawlhalla.core.presentation.models.toWeaponUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme
import kotlinx.coroutines.launch


@Composable
fun WeaponsFilter(
    weapons: List<WeaponUi>,
    onWeaponAction: (WeaponAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val gridState = rememberLazyStaggeredGridState()
    LazyHorizontalStaggeredGrid(
        state = gridState,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp, max = 100.dp),
        rows = StaggeredGridCells.Adaptive(30.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalItemSpacing = 4.dp,
    ) {
        items(weapons, key = { weapon -> weapon.name }) { weapon ->
            WeaponChip(
                weapon, {
                    onWeaponAction(it)
                    coroutineScope.launch {
                        gridState.animateScrollToItem(index = 0)
                    }
                },
                modifier = Modifier.animateItem()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun WeaponsFilterPreview() {
    BrawlhallaTheme {
        Surface {
            WeaponsFilter(
                weapons = (0..30).map { "hammer".toWeaponUi() },
                {}
            )
        }
    }
}