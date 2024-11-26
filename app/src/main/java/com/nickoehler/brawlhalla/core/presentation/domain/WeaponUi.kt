package com.nickoehler.brawlhalla.core.presentation.domain

import com.nickoehler.brawltool.util.getWeaponImageUrlFromWeaponName

data class WeaponUi(
    val name: String,
    val imageUrl: String,
    val selected: Boolean = false
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeaponUi

        if (name != other.name) return false
        if (imageUrl != other.imageUrl) return false
        if (selected != other.selected) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode() + imageUrl.hashCode() + selected.hashCode()
    }
}

fun String.toWeaponUi(selected: Boolean = false): WeaponUi {
    return WeaponUi(this, getWeaponImageUrlFromWeaponName(this), selected)
}