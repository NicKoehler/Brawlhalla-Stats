package com.nickoehler.brawlhalla.core.presentation.util

import android.net.Uri

fun getMiniImageUrlFromLegendNameKey(legendNameKey: String): String {
    return "https://raw.githubusercontent.com/nickoehler/bh-images/refs/heads/main/legends/mini/${
        Uri.encode(
            legendNameKey
        )
    }.png"

}

fun getFullImageUrlFromLegendNameKey(legendNameKey: String): String {
    return "https://raw.githubusercontent.com/nickoehler/bh-images/refs/heads/main/legends/full/${
        Uri.encode(
            legendNameKey
        )
    }.png"
}

