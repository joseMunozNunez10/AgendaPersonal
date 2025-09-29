package com.jmdevs.agendapersonal.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jmdevs.agendapersonal.data.local.dao.EventDao
import com.jmdevs.agendapersonal.data.local.entity.Event
import com.jmdevs.agendapersonal.util.Converters

@Database(entities = [Event::class], version = 2, exportSchema = false) // Versión incrementada a 2
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agenda_database"
                )
                .fallbackToDestructiveMigration() // Añadido para manejar cambios de esquema
                .build()
                INSTANCE = inst
                inst
            }
        }
    }
}
