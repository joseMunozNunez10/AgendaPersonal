package com.jmdevs.agendapersonal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Event entity - primary data model used across the app.
 * `id` is string UUID (we generate it in app with UuidGenerator).
 */
@Entity(tableName = "events")
data class Event(
    @PrimaryKey val id: String,
    val title: String,
    val description: String? = null,
    val dateMillis: Long,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val color: Int? = null,
    val categoryId: String? = null,
    val priority: PriorityLevel = PriorityLevel.MEDIA,
    val tags: List<String>? = null,
    val isRecurring: Boolean = false,
    val recurrenceRule: String? = null
)
