package com.nickoehler.brawlhalla.core.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nickoehler.brawlhalla.core.data.database.entities.Clan

@Dao
interface ClanDao {
    @Query("SELECT * FROM clans")
    fun getAllClans(): LiveData<List<Clan>>

    @Query("SELECT * FROM clans WHERE id=:id")
    suspend fun getClan(id: Int): Clan

    @Insert
    suspend fun insertClan(clan: Clan)

    @Query("DELETE FROM clans WHERE id=:id")
    suspend fun deleteClan(id: Int)
}