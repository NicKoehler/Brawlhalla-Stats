package com.nickoehler.brawlhalla.core.presentation.util

fun getMiniImageUrlFromLegendNameKey(legendNameKey: String): String {
    return "https://raw.githubusercontent.com/nickoehler/bh-images/refs/heads/main/legends/mini/$legendNameKey.png"
}

fun getFullImageUrlFromLegendNameKey(legendNameKey: String): String {
    return "https://raw.githubusercontent.com/nickoehler/bh-images/refs/heads/main/legends/full/$legendNameKey.png"
}

