package com.nickoehler.brawlhalla.core.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nickoehler.brawlhalla.core.data.database.dao.ClanDao
import com.nickoehler.brawlhalla.core.data.database.dao.PlayerDao
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player

@Database(entities = [Player::class, Clan::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun clanDao(): ClanDao
}

fun provideDataBase(application: Application) =
    Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "brawlhalla"
    ).fallbackToDestructiveMigration().build()
