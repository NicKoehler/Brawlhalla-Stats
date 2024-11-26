package com.nickoehler.brawlhalla.legends.domain

data class Legend(
    val legendId: Int,
    val legendNameKey: String,
    val bioName: String,
    val bioAka: String,
    val weaponOne: String,
    val weaponTwo: String,
    val strength: String,
    val dexterity: String,
    val defense: String,
    val speed: String
)
