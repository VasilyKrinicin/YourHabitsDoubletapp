package com.myapp.data.database

object HabitContract {
    const val TABLE_NAME = "habit"

    object Columns {
        const val ID = "id"
        const val UID = "uid"
        const val NAME_HABIT = "name_habit"
        const val DESCRIPTION_HABIT = "description_habit"
        const val TYPE_HABIT = "type_habit"
        const val PRIORITY_HABIT = "priority_habit"
        const val NUMBER_EXECUTIONS = "number_executions"
        const val PERIOD_TEXT = "period_text"
        const val COLOR_HABIT = "color_habit"
        const val DATE = "date"
        const val DONE_DATE = "done_date"
        const val UNLOADED = "unloaded"
    }
}