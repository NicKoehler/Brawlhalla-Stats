package com.nickoehler.brawlhalla.core.data.database

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nickoehler.brawlhalla.core.data.database.dao.GuildDao
import com.nickoehler.brawlhalla.core.data.database.dao.PlayerDao
import com.nickoehler.brawlhalla.core.data.database.entities.Guild
import com.nickoehler.brawlhalla.core.data.database.entities.Player

@Database(
    entities = [Player::class, Guild::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 2, to = 3, spec = Migration2To3::class)
    ]
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun guildDao(): GuildDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE players ADD COLUMN `order` INTEGER NOT NULL DEFAULT 0")
        db.execSQL("ALTER TABLE clans ADD COLUMN `order` INTEGER NOT NULL DEFAULT 0")
    }
}

@RenameTable(
    fromTableName = "clans",
    toTableName = "guilds"
)
class Migration2To3 : AutoMigrationSpec

fun provideDataBase(application: Application) =
    Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "brawlhalla"
    ).addMigrations(MIGRATION_1_2)
        .build()
