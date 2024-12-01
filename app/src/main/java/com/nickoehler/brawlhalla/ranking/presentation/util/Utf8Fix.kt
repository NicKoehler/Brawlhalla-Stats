package com.nickoehler.brawlhalla.ranking.presentation.util

fun String.toFixedUtf8(): String {
    return String(this.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
}