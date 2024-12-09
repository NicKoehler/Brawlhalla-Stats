package com.nickoehler.brawlhalla.legends.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nickoehler.brawlhalla.core.presentation.WeaponAction
import com.nickoehler.brawlhalla.core.presentation.components.WeaponButton
import com.nickoehler.brawlhalla.legends.presentation.models.LegendDetailUi

@Composable
fun LegendDetailToolBar(
    isLoading: Boolean,
    legend: LegendDetailUi?,
    onShow: () -> Unit,
    onWeaponAction: (WeaponAction) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading || legend != null) {

            IconButton(onClick = onShow) {
                Icon(Icons.Default.Info, "lore")
            }
            Spacer(modifier = Modifier.weight(1f))
            WeaponButton(legend?.weaponOne, onWeaponAction)
            WeaponButton(legend?.weaponTwo, onWeaponAction)
        }
    }
}