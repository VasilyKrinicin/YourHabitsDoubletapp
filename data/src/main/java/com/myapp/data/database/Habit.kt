package com.myapp.data.database

import androidx.room.*
import com.myapp.data.Networking.HabitNetwork
import com.myapp.domain.model.PriorityHabit
import com.myapp.domain.model.TypeHabit
import java.util.*


@Entity(tableName = HabitContract.TABLE_NAME)
@TypeConverters(Converters::class)
data class Habit(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = HabitContract.Columns.UID)
    var uid: String,
    @ColumnInfo(name = HabitContract.Columns.NAME_HABIT)
    val nameHabit: String,
    @ColumnInfo(name = HabitContract.Columns.DESCRIPTION_HABIT)
    val descriptionHabit: String,
    @ColumnInfo(name = HabitContract.Columns.TYPE_HABIT)
    val typeHabit: TypeHabit,
    @ColumnInfo(name = HabitContract.Columns.PRIORITY_HABIT)
    val priorityHabit: PriorityHabit,
    @ColumnInfo(name = HabitContract.Columns.NUMBER_EXECUTIONS)
    val numberExecutions: Int,
    @ColumnInfo(name = HabitContract.Columns.PERIOD_TEXT)
    val periodText: Int,
    @ColumnInfo(name = HabitContract.Columns.COLOR_HABIT)
    val colorHabit: Int,
    @ColumnInfo(name = HabitContract.Columns.DATE)
    val date: Int,
    @ColumnInfo(name = HabitContract.Columns.DONE_DATE)
    var doneDate:MutableList<Int>,

    @ColumnInfo(name = HabitContract.Columns.UNLOADED)
    var unloaded: Boolean = false
) {
    fun toHabitNetwork(): HabitNetwork {
        return HabitNetwork(
            uid = uid ,
            color = colorHabit,
            count = numberExecutions,
            description = descriptionHabit,
            frequency = periodText.toInt(),
            type = typeHabit.valueInt,
            doneDates = doneDate,
            date = date,
            priority = priorityHabit.valueInt,
            title = nameHabit

        )
    }
}



