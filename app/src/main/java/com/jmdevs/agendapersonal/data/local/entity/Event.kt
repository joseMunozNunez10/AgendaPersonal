package com.jmdevs.agendapersonal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jmdevs.agendapersonal.utils.Converters

/**
 * Entity que representa la tabla "events" en Room.
 */
@Entity(tableName = "events")
@TypeConverters(Converters::class)                  // Para listas y enums
data class Event(
    @PrimaryKey val id: String,                             // ID único generado con UuidGenerator
    val title: String,                                      // Título del evento
    val description: String? = null,                        // Descripción opcional
    val dateMillis: Long,                                   // Fecha del evento
    val createdAt: Long? = null,                            // Fecha de creación
    val updatedAt: Long? = null,                            // Fecha de última actualización
    val color: Int? = null,                                 // Color para identificar visualmente
    val categoryId: String? = null,                         // Categoría (FK opcional si agregas tabla Category)
    val priority: PriorityLevel = PriorityLevel.MEDIA,      // ALTA, MEDIA, BAJA
    val tags: List<String>? = null,                         // Etiquetas múltiples
    val isRecurring: Boolean = false,                       // Evento recurrente
    val recurrenceRule: String? = null                      // Regla de recurrencia (RRULE)
)
