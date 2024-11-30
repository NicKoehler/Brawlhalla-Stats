package com.nickoehler.brawlhalla.core.presentation.util

fun getWeaponImageUrlFromWeaponName(weaponName: String): String {
    return "https://raw.githubusercontent.com/nickoehler/bh-images/refs/heads/main/weapons/$weaponName.svg"
}