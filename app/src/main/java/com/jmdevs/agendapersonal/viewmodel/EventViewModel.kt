package com.jmdevs.agendapersonal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdevs.agendapersonal.data.local.entity.Event
import com.jmdevs.agendapersonal.data.repository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val repository: EventRepository) : ViewModel() {

    val events: StateFlow<List<Event>> =
        repository.getAllEvents()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getEventByIdFlow(id: String): StateFlow<Event?> =
        repository.getEventById(id)
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun getEventsForDateRange(startMillis: Long, endMillis: Long): Flow<List<Event>> {
        return repository.getEventsBetween(startMillis, endMillis)
    }

    fun insert(event: Event) = viewModelScope.launch { repository.insert(event) }
    fun update(event: Event) = viewModelScope.launch { repository.update(event) }
    fun delete(event: Event) = viewModelScope.launch { repository.delete(event) }
}

