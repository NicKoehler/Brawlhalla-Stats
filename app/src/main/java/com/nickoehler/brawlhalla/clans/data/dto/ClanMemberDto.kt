package com.nickoehler.brawlhalla.clans.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClanMemberDto(

    @SerialName("brawlhalla_id")
    val brawlhallaId: Int,

    @SerialName("name")
    val name: String,

    @SerialName("rank")
    val rank: String,

    @SerialName("join_date")
    val joinDate: Long,

    @SerialName("xp")
    val xp: Int
)