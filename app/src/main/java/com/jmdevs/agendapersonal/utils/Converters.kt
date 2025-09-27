package com.jmdevs.agendapersonal.utils

import androidx.room.TypeConverter
import com.jmdevs.agendapersonal.data.local.entity.PriorityLevel

class Converters {

    @TypeConverter
    fun fromTags(tags:List<String>?): String? = tags?.joinToString { "," }

    @TypeConverter
    fun toTags (data: String?): List<String>? =data?.split(",")

    @TypeConverter
    fun fromPriority(priority: PriorityLevel): String = priority.name

    @TypeConverter
    fun toPriority(value: String): PriorityLevel = PriorityLevel.valueOf(value)
}