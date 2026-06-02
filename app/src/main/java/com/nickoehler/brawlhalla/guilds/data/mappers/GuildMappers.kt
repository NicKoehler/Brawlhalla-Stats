package com.nickoehler.brawlhalla.guilds.data.mappers

import com.nickoehler.brawlhalla.core.presentation.models.toLocalDateTime
import com.nickoehler.brawlhalla.guilds.data.dto.GuildDetailDto
import com.nickoehler.brawlhalla.guilds.data.dto.GuildMemberDto
import com.nickoehler.brawlhalla.guilds.domain.ClanDetail
import com.nickoehler.brawlhalla.guilds.domain.ClanMember
import com.nickoehler.brawlhalla.guilds.domain.ClanRankType

fun GuildDetailDto.toClanDetail(): ClanDetail {
    return ClanDetail(
        id,
        name,
        createDate.toLocalDateTime(),
        xp,
        members.map { it.toClanMember() }
    )
}

fun GuildMemberDto.toClanMember(): ClanMember {
    return ClanMember(
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
        xp
    )
}