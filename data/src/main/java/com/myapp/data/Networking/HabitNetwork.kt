package com.myapp.data.Networking


import com.myapp.data.database.Habit
import com.myapp.domain.model.PriorityHabit
import com.myapp.domain.model.TypeHabit
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
            periodText = frequency,
            colorHabit = color,
            date = date,
            doneDate = doneDates.toMutableList() ?: ArrayList()
        )
    }
}
