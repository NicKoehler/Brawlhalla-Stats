package com.nickoehler.brawlhalla.search.presentation.models

import androidx.compose.ui.graphics.Color
import com.nickoehler.brawlhalla.search.domain.Tier
import com.nickoehler.brawlhalla.ui.theme.BronzeColor
import com.nickoehler.brawlhalla.ui.theme.DiamondColor
import com.nickoehler.brawlhalla.ui.theme.GoldColor
import com.nickoehler.brawlhalla.ui.theme.PlatinumColor
import com.nickoehler.brawlhalla.ui.theme.SilverColor
import com.nickoehler.brawlhalla.ui.theme.TinColor
import com.nickoehler.brawlhalla.ui.theme.ValhallanColor

data class TierUi(
    val name: Tier
)

fun Tier.toTierUi(): TierUi {
    return TierUi(
        name = this
    )
}


fun TierUi.toColor(): Color {
    return when (this.name) {
        Tier.VALHALLAN -> ValhallanColor
        Tier.DIAMOND -> DiamondColor
        Tier.PLATINUM -> PlatinumColor
        Tier.GOLD -> GoldColor
        Tier.SILVER -> SilverColor
        Tier.BRONZE -> BronzeColor
        Tier.TIN -> TinColor
        Tier.UNKNOWN -> Color.Transparent
    }
}