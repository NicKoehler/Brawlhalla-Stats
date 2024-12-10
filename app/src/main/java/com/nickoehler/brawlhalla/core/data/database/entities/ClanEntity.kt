package com.nickoehler.brawlhalla.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clans")
data class Clan(
    @PrimaryKey val id: Int,
    val name: String
)
