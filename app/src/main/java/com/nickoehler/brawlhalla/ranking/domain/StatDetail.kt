package com.nickoehler.brawlhalla.ranking.domain

data class StatDetail(
    val brawlhallaId: Long,
    val name: String,
    val xp: Long?,
    val level: Int?,
    val xpPercentage: Double?,
    val games: Int,
    val wins: Int,
    val damageBomb: Int?,
    val damageMine: Int?,
    val damageSpikeBall: Int?,
    val damageSidekick: Int?,
    val hitSnowball: Int?,
    val koBomb: Int?,
    val koMine: Int?,
    val koSpikeBall: Int?,
    val koSidekick: Int?,
    val koSnowball: Int?,
    val legends: List<StatLegend>,
    val guild: PlayerGuild?,
)
