package com.nickoehler.brawlhalla.core.presentation.util

import android.net.Uri

fun getWeaponImageUrlFromWeaponName(weaponName: String): String {
    return "https://raw.githubusercontent.com/nickoehler/bh-images/refs/heads/main/weapons/${
        Uri.encode(
            weaponName
        )
    }.svg"
}