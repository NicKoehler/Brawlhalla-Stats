package com.nickoehler.brawlhalla.guilds.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberDto(

    @SerialName("brawlhalla_id")
    val brawlhallaId: Long,

    @SerialName("name")
    val name: String,

    @SerialName("rank")
    val rank: String,

    @SerialName("join_date")
    val joinDate: Long,

    @SerialName("xp")
    val xp: Long,

    @SerialName("guild_points")
    val guildPoints: Int,
)