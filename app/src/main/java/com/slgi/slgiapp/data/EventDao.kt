package com.slgi.slgiapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM EventsTable")
    fun getAll(): Flow<List<Event>>

    @Query("SELECT * FROM EventsTable WHERE User_Partakes")
    fun getUserEvents(): Flow<List<Event>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)


}