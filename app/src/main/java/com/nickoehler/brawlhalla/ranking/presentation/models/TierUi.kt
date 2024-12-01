package com.nickoehler.brawlhalla.ranking.presentation.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.nickoehler.brawlhalla.R
import com.nickoehler.brawlhalla.ranking.domain.Tier
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


@Composable
fun TierUi.toLocalizedString(): String {
    return stringResource(
        when (this.name) {
            Tier.VALHALLAN -> R.string.tier_valhallan
            Tier.DIAMOND -> R.string.tier_diamond
            Tier.PLATINUM -> R.string.tier_platinum
            Tier.GOLD -> R.string.tier_gold
            Tier.SILVER -> R.string.tier_silver
            Tier.BRONZE -> R.string.tier_bronze
            Tier.TIN -> R.string.tier_tin
            Tier.UNKNOWN -> R.string.tier_unknown
        }
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