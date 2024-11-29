package com.nickoehler.brawlhalla.core.presentation

import com.nickoehler.brawlhalla.core.presentation.models.WeaponUi

sealed interface WeaponAction {
    data class Click(val weapon: WeaponUi) : WeaponAction
    data class Select(val weapon: WeaponUi) : WeaponAction
}
