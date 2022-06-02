package com.myapp.data.database

import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.SortType
import com.myapp.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class FakeDAO(var habits: MutableList<Habit> = mutableListOf()) :
    HabitDao {
    override fun getAllItems(): Flow<List<Habit>> {
        return flowOf(habits.toList())
    }

    override fun getHabitByUID(uid: String): Flow<Habit> {
        var flowHabit= flowOf<Habit>()
        for (habit in habits){
            if (habit.uid==uid){
                flowHabit = flowOf(habit)
            }
        }
        return flowHabit
    }

    override suspend fun insertHabit(habit: Habit) {
        habits.add(habit)
    }

    override suspend fun updateHabit(habit: Habit) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHabit(habit: Habit) {
        habits.remove(habit)
    }

    override fun getHabitByType(typeHabit: String): Flow<List<Habit>> {
        return flowOf( habits.filter { it.typeHabit.str == typeHabit })
       /* val resultList= mutableListOf<Habit>()
        habits.forEach {
            if(it.typeHabit.str == typeHabit){
                resultList.add(it)
            }
        }
        return flowOf(resultList.toList())*/
    }

    override fun getSortFilterHabitDESC(text: String): Flow<List<Habit>> {
        TODO("Not yet implemented")
    }

    override fun getFilterHabit(text: String): Flow<List<Habit>> {
        val resultListHabit= mutableListOf<Habit>()
        habits.forEachIndexed{index, habit ->
            if (text.contains(habit.nameHabit,ignoreCase = true))
                resultListHabit.add(habit)
        }
        return  flowOf (resultListHabit.toList() )
    }

    override fun getSortFilterHabitASC(text: String): Flow<List<Habit>> {
        TODO("Not yet implemented")
    }

}