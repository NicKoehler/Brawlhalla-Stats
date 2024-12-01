package com.nickoehler.brawlhalla.ranking.domain

data class StatDetail(

    val brawlhallaId: Int,
    val name: String,
    val xp: Double,
    val level: Int,
    val xpPercentage: Int,
    val games: Int,
    val wins: Int,
    val damageBomb: Int,
    val damageMine: Int,
    val damageSpikeball: Int,
    val damageSidekick: Int,
    val hitSnowball: Int,
    val koBomb: Int,
    val koMine: Int,
    val koSpikeball: Int,
    val koSidekick: Int,
    val koSnowball: Int,
    val legends: List<StatLegend>,
    val clan: StatClan? = null,
)
