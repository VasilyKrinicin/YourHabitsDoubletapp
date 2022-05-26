package com.myapp.data.database

import androidx.room.TypeConverter
import com.myapp.domain.model.PriorityHabit
import com.myapp.domain.model.TypeHabit
import java.util.*
import java.util.stream.Collectors

class Converters {
    @TypeConverter

    fun fromTypeHabit(typeHabit: TypeHabit): String {
        return typeHabit.str
    }

    @TypeConverter
    fun toTypeHabit(data: String): TypeHabit {
        return when (data) {
            TypeHabit.POSITIVE.str -> {
                TypeHabit.POSITIVE
            }
            else -> {
                TypeHabit.NEGATIVE
            }
        }
    }

    @TypeConverter
    fun fromPriorityHabit(priorityHabit: PriorityHabit): String {
        return priorityHabit.str
    }

    @TypeConverter
    fun toPriorityHabit(data: String): PriorityHabit {
        return when (data) {
            PriorityHabit.HIGH.str -> {
                PriorityHabit.HIGH
            }
            PriorityHabit.MIDDLE.str -> {
                PriorityHabit.MIDDLE
            }
            else -> {
                PriorityHabit.LOW
            }
        }
    }

    @TypeConverter
    fun fromList(date: List<Int?>): String? {
        return date.joinToString()
    }

    @TypeConverter
    fun toList(data: String): List<Int?>? {
        val number = Regex("0-9").findAll(data)
            .map(MatchResult::value)
            .toList()
        return number.map { it.toInt() }

    }
}