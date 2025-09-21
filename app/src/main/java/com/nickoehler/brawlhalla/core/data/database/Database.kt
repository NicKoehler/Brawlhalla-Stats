package com.nickoehler.brawlhalla.core.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nickoehler.brawlhalla.core.data.database.dao.ClanDao
import com.nickoehler.brawlhalla.core.data.database.dao.PlayerDao
import com.nickoehler.brawlhalla.core.data.database.entities.Clan
import com.nickoehler.brawlhalla.core.data.database.entities.Player

@Database(entities = [Player::class, Clan::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun clanDao(): ClanDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE players ADD COLUMN `order` INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE clans ADD COLUMN `order` INTEGER NOT NULL DEFAULT 0")
    }
}

fun provideDataBase(application: Application) =
    Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "brawlhalla"
    ).addMigrations(MIGRATION_1_2)
        .build()
