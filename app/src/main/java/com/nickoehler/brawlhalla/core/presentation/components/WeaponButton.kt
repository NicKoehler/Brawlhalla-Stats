package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.domain.WeaponUi

@Composable
fun WeaponButton(
    weapon: WeaponUi,
    onClick: (WeaponAction) -> Unit,
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier.size(40.dp),
        horizontalArrangement = Arrangement.Center,
        contentPadding = 8.dp,
        borderRadius = 15.dp,
        onClick = { onClick(WeaponAction.Click(weapon)) },
        color = MaterialTheme.colorScheme.primary,
    ) {
        AsyncImage(
            weapon.imageUrl,
            weapon.name,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceBright),
        )
    }
}