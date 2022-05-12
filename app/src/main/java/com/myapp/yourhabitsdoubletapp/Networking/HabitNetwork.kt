package com.myapp.yourhabitsdoubletapp.Networking

import com.myapp.yourhabitsdoubletapp.Data.PriorityHabit
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.database.Habit
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UID(@Json(name = "uid") val uid: String)

@JsonClass(generateAdapter = true)
class HabitNetwork(
    val color: Int,
    val count: Int,
    val description: String,
    val date: Int,
    @Json(name = "done_dates") val doneDates: List<Int>,
    val frequency: Int,
    val priority: Int,
    val title: String,
    val type: Int,
    val uid: String
) {


    fun toHabit(): Habit {
        return Habit(
            id = 0,
            uid = uid,
            nameHabit = title,
            descriptionHabit = description,
            typeHabit = when (type) {
                TypeHabit.POSITIVE.valueInt -> {
                    TypeHabit.POSITIVE
                }
                else -> {
                    TypeHabit.NEGATIVE
                }
            },
            priorityHabit = when (priority) {
                PriorityHabit.LOW.valueInt -> PriorityHabit.LOW
                PriorityHabit.MIDDLE.valueInt -> PriorityHabit.MIDDLE
                else -> PriorityHabit.HIGH
            },
            numberExecutions = count,
            periodText = frequency.toString(),
            colorHabit = color,
            date = date
        )
    }
}
