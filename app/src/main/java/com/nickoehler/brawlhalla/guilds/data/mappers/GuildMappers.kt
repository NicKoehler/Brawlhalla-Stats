package com.nickoehler.brawlhalla.guilds.data.mappers

import com.nickoehler.brawlhalla.core.presentation.models.toLocalDateTime
import com.nickoehler.brawlhalla.guilds.data.dto.GuildDetailDto
import com.nickoehler.brawlhalla.guilds.data.dto.GuildMemberDto
import com.nickoehler.brawlhalla.guilds.domain.GuildDetail
import com.nickoehler.brawlhalla.guilds.domain.GuildMember
import com.nickoehler.brawlhalla.guilds.domain.GuildRankType

fun GuildDetailDto.toGuildDetail(members: List<GuildMember>): GuildDetail {
    return GuildDetail(
        id,
        name,
        createDate.toLocalDateTime(),
        xp,
        legacyXp,
        notice,
        tags,
        discordInviteCode,
        guildPoints,
        rank,
        isRecruiting,
        members
    )
}

fun GuildMemberDto.toGuildMember(): GuildMember {
    return GuildMember(
        brawlhallaId,
        name,
        when (rank) {
            "Leader" -> GuildRankType.Leader
            "Officer" -> GuildRankType.Officer
            "Member" -> GuildRankType.Member
            "Recruit" -> GuildRankType.Recruit
            else -> GuildRankType.Unknown
        },
        joinDate.toLocalDateTime(),
        xp,
        guildPoints
    )
}