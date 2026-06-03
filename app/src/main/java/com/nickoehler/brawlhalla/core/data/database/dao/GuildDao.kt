package com.nickoehler.brawlhalla.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import kotlinx.coroutines.flow.Flow

@Dao
interface GuildDao {
    @Query("SELECT * FROM guilds ORDER by `order` ASC")
    fun getAllGuilds(): Flow<List<Guild>>

    @Query("SELECT * FROM guilds WHERE id=:id")
    fun getGuild(id: Long): Flow<Guild?>

    @Insert
    suspend fun insertGuild(guild: Guild)

    @Query("DELETE FROM guilds WHERE id=:id")
    suspend fun deleteGuild(id: Long)

    @Update
    suspend fun updateGuilds(guilds: List<Guild>)
}