package com.jmdevs.agendapersonal.data.repository

import com.jmdevs.agendapersonal.data.local.dao.EventDao
import com.jmdevs.agendapersonal.data.local.entity.Event
import kotlinx.coroutines.flow.Flow

class EventRepository(private val dao: EventDao) {

    fun getAllEvents(): Flow<List<Event>> = dao.getAllEvents()

    fun getEventById(id: String): Flow<Event?> = dao.getEventById(id)

    fun searchEvents(query: String): Flow<List<Event>> = dao.searchEvents(query)

    fun getEventsBetween(startMillis: Long, endMillis: Long): Flow<List<Event>> =
        dao.getEventsBetween(startMillis, endMillis)

    suspend fun insert(event: Event) = dao.insert(event)

    suspend fun update(event: Event) = dao.update(event)

    suspend fun delete(event: Event) = dao.delete(event)

    suspend fun isCompleted(event: Event) = dao.isCompleted(event)
}
