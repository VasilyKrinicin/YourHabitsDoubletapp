package com.myapp.yourhabitsdoubletapp.ViewModel

import com.myapp.yourhabitsdoubletapp.Data.SortType
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.Habit

object HabitRepository {
    private var listHabit = arrayListOf<Habit>()


    fun addHabit(habit: Habit): ArrayList<Habit> {
        val arrayListHabit = getHabitList()
        arrayListHabit.add(habit)
        return arrayListHabit
    }

    fun editHabitList(newHabit: Habit): ArrayList<Habit> {
        var position = 0
        listHabit.forEachIndexed { index, habit ->
            if (habit.id == newHabit.id) {
                position = index
            }
        }
        listHabit[position] = newHabit
        return listHabit
    }

    fun filterAndSort(habitList: List<Habit>, sortType: SortType, text: String?): List<Habit> {
        val filterList = filterList(habitList, text)
        return sortList(filterList, sortType)
    }

    private fun filterList(habitList: List<Habit>, text: String?): List<Habit> {
        return habitList.filter { it.nameHabit.contains(text ?: "", true) }
    }

    private fun sortList(habitList: List<Habit>, sortType: SortType): List<Habit> {
        var habitListSort = habitList
        habitListSort.toMutableList()
        when (sortType) {
            SortType.AZ -> {
                habitListSort = habitList.sortedBy { it.nameHabit }
            }
            SortType.ZA -> {
                habitListSort = habitList.sortedByDescending { it.nameHabit }
            }
            SortType.NONE -> {}
        }
        return habitListSort
    }

    fun getHabitList() = listHabit
}