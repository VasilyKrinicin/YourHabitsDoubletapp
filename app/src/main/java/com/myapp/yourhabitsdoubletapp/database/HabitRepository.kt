package com.myapp.yourhabitsdoubletapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.myapp.yourhabitsdoubletapp.Data.SortType
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

object HabitRepository {
    private lateinit var instance: HabitDataBase

    fun getDatabase(context: Context): HabitDataBase {
        instance = Room.databaseBuilder(
            context,
            HabitDataBase::class.java,
            HabitDataBase.DB_NAME
        )
            .build()
        return instance
    }

    fun getAll(): LiveData<List<Habit>> = instance.habitDao().getAllItems()

    fun getSortFilterListHabit(
        typeHabit: TypeHabit?,
        sortType: SortType?,
        text: String?
    ): LiveData<List<Habit>> {
        return if (typeHabit == null) {
            instance.habitDao().getAllItems()
        } else
            when (sortType) {
                SortType.AZ -> {
                    instance.habitDao().getSortFilterHabitASC(typeHabit.str, text ?: "")
                }
                SortType.ZA -> {
                    instance.habitDao().getSortFilterHabitDESC(typeHabit.str, text ?: "")
                }
                else -> {
                    instance.habitDao().getFilterHabit(typeHabit.str, text ?: "")
                }
            }
    }

    suspend fun getHabitById(id: Long): Habit {
        return instance.habitDao().getHabitById(id)
    }

    suspend fun addHabit(habit: Habit) {
        instance.habitDao().insertHabit(habit)
    }

    suspend fun editHabitList(newHabit: Habit) {
        instance.habitDao().updateHabit(newHabit)
    }
}