package com.jmdevs.agendapersonal.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jmdevs.agendapersonal.data.local.entity.Event
import kotlinx.coroutines.flow.Flow

interface EventDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM events ORDER BY dateMillis ASC")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY dateMillis ASC")
    fun searchEvents(query: String): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE dateMillis BETWEEN :startMillis AND :endMillis ORDER BY dateMillis ASC")
    fun getEventsBetween(startMillis: Long, endMillis: Long): Flow<List<Event>>

}