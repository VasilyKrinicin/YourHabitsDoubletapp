package com.myapp.yourhabitsdoubletapp.database

import androidx.room.TypeConverter
import com.myapp.yourhabitsdoubletapp.Data.PriorityHabit
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit

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
}