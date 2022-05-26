package com.myapp.data.convert

import com.myapp.data.database.Habit
import com.myapp.domain.model.HabitModel

class ConvertModel {
    companion object {
        fun convertHabitToHabitModel(habit: Habit): HabitModel = HabitModel(
            uid = habit.uid,
            nameHabit = habit.nameHabit,
            descriptionHabit = habit.descriptionHabit,
            typeHabit = habit.typeHabit,
            priorityHabit = habit.priorityHabit,
            numberExecutions = habit.numberExecutions,
            periodText = habit.periodText,
            colorHabit = habit.colorHabit,
            date = habit.date,
            unloaded = habit.unloaded,
            doneDate = habit.doneDate
        )

        fun convertHabitModelToHabit(habitModel: HabitModel): Habit = Habit(
            uid = habitModel.uid,
            nameHabit = habitModel.nameHabit,
            descriptionHabit = habitModel.descriptionHabit,
            typeHabit = habitModel.typeHabit,
            priorityHabit = habitModel.priorityHabit,
            numberExecutions = habitModel.numberExecutions,
            periodText = habitModel.periodText,
            colorHabit = habitModel.colorHabit,
            date = habitModel.date,
            doneDate = habitModel.doneDate,
            unloaded = habitModel.unloaded
        )

        fun convertToHabitModelList(habitList: List<Habit>): List<HabitModel> {
            return habitList.map { convertHabitToHabitModel(it) }
        }

        fun convertToHabitList(habitList: List<HabitModel>): List<Habit> {
            return habitList.map { convertHabitModelToHabit(it) }
        }
    }

}