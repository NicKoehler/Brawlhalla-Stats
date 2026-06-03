package com.nickoehler.brawlhalla.guilds.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GuildMemberResponseDto(
    @SerialName("guild_id")
    val id: Int,
    @SerialName("guild_members")
    val guildMembers: List<GuildMemberDto>
)
