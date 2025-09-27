package com.nickoehler.brawlhalla.clans.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClanDetailDto(

    @SerialName("clan_id")
    val id: Long,

    @SerialName("clan_name")
    val name: String,

    @SerialName("clan_create_date")
    val createDate: Long,

    @SerialName("clan_xp")
    val xp: Long,

    @SerialName("clan")
    val members: List<ClanMemberDto>
)