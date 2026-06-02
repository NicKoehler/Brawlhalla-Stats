package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerGuildDto(
    @SerialName("guild_id")
    val guildId: Long,
    @SerialName("guild_name")
    val guildName: String,
    @SerialName("personal_xp")
    val personalXp: Long,
    @SerialName("personal_xp_this_week")
    val personalXpThisWeek: Long,
    @SerialName("personal_points")
    val personalPoints: Long,
    @SerialName("join_date")
    val joinDate: Long,
    @SerialName("rank")
    val rank: String,
)
