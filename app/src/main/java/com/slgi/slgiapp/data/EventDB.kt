package com.slgi.slgiapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class], exportSchema = false, version = 1)
abstract class EventDB : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var Instance: EventDB? = null

        fun getDatabase(context: Context): EventDB {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, EventDB::class.java,"event_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
