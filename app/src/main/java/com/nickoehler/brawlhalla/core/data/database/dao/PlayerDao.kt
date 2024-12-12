package com.nickoehler.brawlhalla.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<Player>>

    @Query("SELECT * FROM players WHERE id=:id")
    fun getPlayer(id: Int): Flow<Player?>

    @Insert
    suspend fun insertPlayer(player: Player)

    @Query("DELETE FROM players WHERE id=:id")
    suspend fun deletePlayer(id: Int)
}