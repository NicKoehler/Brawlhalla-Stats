package com.nickoehler.brawlhalla.guilds.domain

import java.time.LocalDateTime


data class GuildDetail(
    val id: Long,
    val name: String,
    val createDate: LocalDateTime,
    val xp: Long,
    val legacyXp: Long,
    val notice: String,
    val tags: List<String>,
    val discordInviteCode: String,
    val guildPoints: Int,
    val rank: Int?,
    val isRecruiting: Boolean,
    val members: List<GuildMember>
)