package com.nickoehler.brawlhalla.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import kotlinx.coroutines.flow.Flow

@Dao
interface ClanDao {
    @Query("SELECT * FROM clans")
    fun getAllClans(): Flow<List<Clan>>

    @Query("SELECT * FROM clans WHERE id=:id")
    fun getClan(id: Long): Flow<Clan?>

    @Insert
    suspend fun insertClan(clan: Clan)

    @Query("DELETE FROM clans WHERE id=:id")
    suspend fun deleteClan(id: Long)
}