package com.nickoehler.brawlhalla.core.domain.model

enum class Theme(val value: Int) {
    System(0),
    Light(1),
    Dark(2);

    companion object {
        fun fromInt(value: Int) = entries.first { it.value == value }
    }
}
