package com.nickoehler.brawlhalla.guilds.data.mappers

import com.nickoehler.brawlhalla.core.presentation.models.toLocalDateTime
import com.nickoehler.brawlhalla.guilds.data.dto.GuildDetailDto
import com.nickoehler.brawlhalla.guilds.data.dto.GuildMemberDto
import com.nickoehler.brawlhalla.guilds.domain.ClanRankType
import com.nickoehler.brawlhalla.guilds.domain.GuildDetail
import com.nickoehler.brawlhalla.guilds.domain.GuildMember

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
            "Leader" -> ClanRankType.Leader
            "Officer" -> ClanRankType.Officer
            "Member" -> ClanRankType.Member
            "Recruit" -> ClanRankType.Recruit
            else -> ClanRankType.Unknown
        },
        joinDate.toLocalDateTime(),
        xp,
        guildPoints
    )
}