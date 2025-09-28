package com.jmdevs.agendapersonal.util

import androidx.room.TypeConverter
import com.jmdevs.agendapersonal.data.local.entity.PriorityLevel

class Converters {

    // Tags converter - use '|' as separator (less likely to appear in tags)
    @TypeConverter
    fun fromTags(tags: List<String>?): String? = tags?.joinToString("|")

    @TypeConverter
    fun toTags(data: String?): List<String>? =
        data?.takeIf { it.isNotEmpty() }?.split("|")

    // Priority enum <-> String
    @TypeConverter
    fun fromPriority(priority: PriorityLevel): String = priority.name

    @TypeConverter
    fun toPriority(value: String): PriorityLevel = PriorityLevel.valueOf(value)
}
