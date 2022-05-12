package com.myapp.yourhabitsdoubletapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.myapp.yourhabitsdoubletapp.Data.SortType
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object HabitRepository : CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }


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


    fun getAll(): List<Habit> = instance.habitDao().getAllItems()

    fun getSortFilterListHabit(
        typeHabit: TypeHabit?,
        sortType: SortType?,
        text: String?
    ): LiveData<List<Habit>> {
        return if (typeHabit == null) {
            instance.habitDao().getFilterHabit(TypeHabit.POSITIVE.str, text ?: "")
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

    suspend fun getHabitByUID(uid: String): Habit = coroutineScope {
        withContext(Dispatchers.IO) {
            instance.habitDao().getHabitByUID(uid)
        }
    }

    suspend fun getHabitById(id: Long): Habit = coroutineScope {
        withContext(Dispatchers.IO) {
            instance.habitDao().getHabitById(id)
        }
    }

    fun addHabit(habit: Habit) = launch(Dispatchers.IO) {
        instance.habitDao().insertHabit(habit)
    }

    suspend fun editHabit(newHabit: Habit) = launch(Dispatchers.IO) {
        instance.habitDao().updateHabit(newHabit)
    }


}