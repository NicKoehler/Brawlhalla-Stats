package com.nickoehler.brawlhalla.ranking.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClanDetailDto(

    @SerialName("clan_id")
    val id: Int,

    @SerialName("clan_name")
    val name: String,

    @SerialName("clan_create_date")
    val createDate: Long,

    @SerialName("clan_xp")
    val xp: Int,

    @SerialName("clan")
    val members: List<ClanMemberDto>
)