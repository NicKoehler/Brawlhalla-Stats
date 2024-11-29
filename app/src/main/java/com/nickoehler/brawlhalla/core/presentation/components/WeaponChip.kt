package com.nickoehler.brawlhalla.core.presentation.components

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import coil3.svg.SvgDecoder
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.models.WeaponUi
import com.nickoehler.brawlhalla.core.presentation.models.toWeaponUi
import com.nickoehler.brawlhalla.ui.theme.BrawlhallaTheme


@Composable
fun WeaponChip(
    weapon: WeaponUi,
    onClick: (WeaponAction) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        weapon.selected,
        modifier = modifier,
        onClick = {
            onClick(WeaponAction.Select(weapon))
        },
        leadingIcon = {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .decoderFactory(SvgDecoder.Factory())
                    .data(weapon.imageUrl).size(Size.ORIGINAL)
                    .build()
            )
            Icon(
                painter,
                weapon.name,
                modifier = Modifier.requiredSize(FilterChipDefaults.IconSize),
            )
        },
        label = {
            Text(weapon.name)
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