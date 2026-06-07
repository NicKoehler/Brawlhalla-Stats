package com.nickoehler.brawlhalla.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "guilds")
data class Guild(
    @PrimaryKey 
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("order")
    val order: Int
)
