package com.nickoehler.brawlhalla.core.presentation.util

fun getMiniImageUrlFromLegendId(legendId: Long): String {
    return "https://raw.githubusercontent.com/nickoehler/bh-images/refs/heads/main/legends/mini/${
        legendId
    }.png"

}

fun getFullImageUrlFromLegendId(legendId: Long): String {
    return "https://raw.githubusercontent.com/nickoehler/bh-images/refs/heads/main/legends/full/${
        legendId
    }.png"
}

