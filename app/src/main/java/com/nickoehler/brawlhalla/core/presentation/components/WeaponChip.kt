package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.domain.WeaponUi
import com.nickoehler.brawlhalla.core.presentation.domain.toWeaponUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme


@Composable
fun WeaponChip(
    weapon: WeaponUi,
    onClick: (WeaponAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColor = if (weapon.selected)
        MaterialTheme.colorScheme.surface
    else
        MaterialTheme.colorScheme.onSurface
    FilterChip(
        weapon.selected,
        modifier = modifier,
        onClick = {
            onClick(WeaponAction.Select(weapon))
        },
        colors = FilterChipDefaults.filterChipColors().copy(
            selectedLabelColor = selectedColor
        ),
        label = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    weapon.imageUrl,
                    weapon.name,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(selectedColor)
                )
                Text(weapon.name)
            }
        },
    )
}

@PreviewLightDark
@Composable
private fun WeaponChipPreview() {
    BrawlhallaTheme {
        Surface {
            WeaponChip(
                "hammer".toWeaponUi(),
                {},
            )
        }
    }
}