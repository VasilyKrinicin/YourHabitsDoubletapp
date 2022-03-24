package com.myapp.yourhabitsdoubletapp

import android.os.Parcelable
import com.myapp.yourhabitsdoubletapp.Data.PriorityHabit
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    var id: Int,
    val nameHabit: String,
    val descriptionHabit: String,
    val typeHabit: TypeHabit,
    val priorityHabit: PriorityHabit,
    val numberExecutions: Int,
    val periodText: String,
    val colorHabit: Int
) : Parcelable


