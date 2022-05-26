package com.myapp.domain.model


class HabitModel(
    var uid: String,
    val nameHabit: String,
    val descriptionHabit: String,
    val typeHabit: TypeHabit,
    val priorityHabit: PriorityHabit,
    val numberExecutions: Int,
    val periodText: Int,
    val colorHabit: Int,
    val date: Int,
    val doneDate: MutableList<Int>,
    var unloaded: Boolean = false
)

