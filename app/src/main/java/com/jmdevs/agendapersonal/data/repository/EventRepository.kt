package com.jmdevs.agendapersonal.data.repository

import com.jmdevs.agendapersonal.data.local.dao.EventDao
import com.jmdevs.agendapersonal.data.local.entity.Event
import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {

    fun getAllEvents(): Flow<List<Event>> = eventDao.getAllEvents()

    fun searchEvents(query: String): Flow<List<Event>> = eventDao.searchEvents(query)

    fun getEventsBetween(startMillis: Long, endMillis: Long): Flow<List<Event>> =
        eventDao.getEventsBetween(startMillis, endMillis)

    suspend fun insert(event: Event) = eventDao.insert(event)

    suspend fun update(event: Event) = eventDao.update(event)

    suspend fun delete(event: Event) = eventDao.delete(event)
}
