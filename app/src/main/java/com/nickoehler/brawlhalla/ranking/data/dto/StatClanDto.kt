package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatClanDto(
    @SerialName("clan_name")
    val clanName: String,
    @SerialName("clan_id")
    val clanId: Int,
    @SerialName("clan_xp")
    val clanXp: Int,
    @SerialName("personal_xp")
    val personalXp: Int,
)
