package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.models.WeaponUi
import com.nickoehler.brawlhalla.core.presentation.models.toWeaponUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme

@Composable
fun WeaponButton(
    weapon: WeaponUi? = null,
    onClick: (WeaponAction) -> Unit = {},
    modifier: Modifier = Modifier
) {
    CustomCard(
        modifier = modifier.size(35.dp),
        horizontalArrangement = Arrangement.Center,
        contentPadding = if (weapon != null) 8.dp else 0.dp,
        borderRadius = 12.dp,
        onClick = { if (weapon != null) onClick(WeaponAction.Click(weapon)) },
        color = MaterialTheme.colorScheme.primary,
    ) {
        if (weapon != null) {
            AsyncImage(
                weapon.imageUrl,
                weapon.name,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.surfaceBright),
            )
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .shimmerEffect()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun WeaponButtonPreview() {

    BrawlhallaTheme {
        Surface {

            Column {

                WeaponButton(
                    "hammer".toWeaponUi(),
                    {}
                )

                WeaponButton()
            }

        }
    }
}