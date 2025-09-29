package com.jmdevs.agendapersonal.data.local.dao

import androidx.room.*
import com.jmdevs.agendapersonal.data.local.entity.Event
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Update
    suspend fun isCompleted(event: Event)

    @Delete
    suspend fun delete(event: Event)

    @Query("SELECT * FROM events ORDER BY dateMillis ASC")
    fun getAllEvents(): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE id = :id LIMIT 1")
    fun getEventById(id: String): Flow<Event?>

    @Query("SELECT * FROM events WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY dateMillis ASC")
    fun searchEvents(query: String): Flow<List<Event>>

    @Query("SELECT * FROM events WHERE dateMillis BETWEEN :startMillis AND :endMillis ORDER BY dateMillis ASC")
    fun getEventsBetween(startMillis: Long, endMillis: Long): Flow<List<Event>>
}
