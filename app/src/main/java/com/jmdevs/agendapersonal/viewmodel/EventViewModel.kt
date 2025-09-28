package com.jmdevs.agendapersonal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdevs.agendapersonal.data.local.entity.Event
import com.jmdevs.agendapersonal.data.repository.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    // Reactive stream of events
    val events: StateFlow<List<Event>> =
        repository.getAllEvents()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Helper to observe a single event as StateFlow (UI can collect)
    fun getEventByIdFlow(id: String): StateFlow<Event?> =
        repository.getEventById(id)
            .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun insert(event: Event) = viewModelScope.launch { repository.insert(event) }
    fun update(event: Event) = viewModelScope.launch { repository.update(event) }
    fun delete(event: Event) = viewModelScope.launch { repository.delete(event) }
}
