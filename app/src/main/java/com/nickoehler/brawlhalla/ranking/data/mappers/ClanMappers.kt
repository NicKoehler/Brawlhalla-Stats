package com.nickoehler.brawlhalla.ranking.data.mappers

import com.nickoehler.brawlhalla.ranking.data.dto.ClanDetailDto
import com.nickoehler.brawlhalla.ranking.data.dto.ClanMemberDto
import com.nickoehler.brawlhalla.ranking.domain.ClanDetail
import com.nickoehler.brawlhalla.ranking.domain.ClanMember
import java.time.LocalDateTime
import java.time.ZoneOffset

fun ClanDetailDto.toClanDetail(): ClanDetail {
    return ClanDetail(
        id,
        name,
        LocalDateTime.ofEpochSecond(createDate, 0, ZoneOffset.UTC),
        xp,
        members.map { it.toClanMember() }
    )
}

fun ClanMemberDto.toClanMember(): ClanMember {
    return ClanMember(
        brawlhallaId,
        name,
        rank,
        LocalDateTime.ofEpochSecond(joinDate, 0, ZoneOffset.UTC),
        xp
    )
}