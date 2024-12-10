package com.nickoehler.brawlhalla.core.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nickoehler.brawlhalla.core.data.database.entities.Player

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players")
    fun getAllPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM players WHERE id=:id")
    suspend fun getPlayer(id: Int): Player

    @Insert
    suspend fun insertPlayer(player: Player)

    @Query("DELETE FROM players WHERE id=:id")
    suspend fun deletePlayer(id: Int)
}