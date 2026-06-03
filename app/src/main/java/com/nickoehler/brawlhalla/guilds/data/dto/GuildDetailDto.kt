package com.nickoehler.brawlhalla.guilds.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildDetailDto(

    @SerialName("guild_id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("create_date")
    val createDate: Long,
    @SerialName("xp")
    val xp: Long,
    @SerialName("legacy_xp")
    val legacyXp: Long,
    val notice: String,
    val tags: List<String>,
    @SerialName("discord_invite_code")
    val discordInviteCode: String,
    @SerialName("guild_points")
    val guildPoints: Int,
    val rank: Int?,
    @SerialName("is_recruiting")
    val isRecruiting: Boolean,
    @SerialName("member_count")
    val memberCount: Int,
)