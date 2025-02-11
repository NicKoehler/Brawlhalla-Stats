package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.clans.data.dto.ClanDetailDto
import com.nickoehler.brawlhalla.clans.data.dto.ClanMemberDto
import com.nickoehler.brawlhalla.clans.domain.ClanDetail
import com.nickoehler.brawlhalla.clans.domain.ClanMember
import com.nickoehler.brawlhalla.core.presentation.models.toLocalDateTime

fun ClanDetailDto.toClanDetail(): ClanDetail {
    return ClanDetail(
        id,
        name,
        createDate.toLocalDateTime(),
        xp,
        members.map { it.toClanMember() }
    )
}

fun ClanMemberDto.toClanMember(): ClanMember {
    return ClanMember(
        brawlhallaId,
        name,
        rank,
        joinDate.toLocalDateTime(),
        xp
    )
}