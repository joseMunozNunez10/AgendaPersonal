package com.jmdevs.agendapersonal.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdevs.agendapersonal.data.local.entity.Event
import com.jmdevs.agendapersonal.data.repository.EventRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    val events: StateFlow<List<Event>> =
        repository.getAllEvents()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insert(event: Event) {
        viewModelScope.launch { repository.insert(event) }
    }

    fun update(event: Event) {
        viewModelScope.launch { repository.update(event) }
    }

    fun delete(event: Event) {
        viewModelScope.launch { repository.delete(event) }
    }
}
