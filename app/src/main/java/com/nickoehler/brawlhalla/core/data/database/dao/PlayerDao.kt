package com.nickoehler.brawlhalla.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nickoehler.brawlhalla.core.data.database.entities.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Query("SELECT * FROM players ORDER by `order` ASC")
    fun getAllPlayers(): Flow<List<Player>>

    @Query("SELECT * FROM players WHERE id=:id")
    fun getPlayer(id: Long): Flow<Player?>

    @Insert
    suspend fun insertPlayer(player: Player)

    @Query("DELETE FROM players WHERE id=:id")
    suspend fun deletePlayer(id: Long)

    @Update
    suspend fun updatePlayers(players: List<Player>)
}