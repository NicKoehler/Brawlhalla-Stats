package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.StatLegendDto
import com.nickoehler.brawlhalla.ranking.domain.StatLegend

fun StatLegendDto.toStatLegend(): StatLegend {
    return StatLegend(
        legendId,
        legendNameKey,
        damageDealt,
        damageTaken,
        kos,
        falls,
        suicides,
        teamKos,
        matchTime,
        games,
        wins,
        damageUnarmed,
        damageThrownItem,
        damageWeaponOne,
        damageWeaponTwo,
        damageGadgets,
        koUnarmed,
        koThrownItem,
        koWeaponOne,
        koWeaponTwo,
        koGadgets,
        timeHeldWeaponOne,
        timeHeldWeaponTwo,
        xp,
        level,
        xpPercentage
    )
}