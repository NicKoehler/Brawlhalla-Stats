package com.nickoehler.brawlhalla.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "players")
data class Player(
    @PrimaryKey val id: Long,
    val name: String
)
