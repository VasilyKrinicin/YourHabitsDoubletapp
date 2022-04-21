package com.myapp.yourhabitsdoubletapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.myapp.yourhabitsdoubletapp.Data.SortType
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit

object HabitRepository {
    lateinit var instance: HabitDataBase
        private set

    fun getDatabase(context: Context): HabitDataBase {
        instance = Room.databaseBuilder(
            context,
            HabitDataBase::class.java,
            HabitDataBase.DB_NAME
        )
            .allowMainThreadQueries()
            .build()
        return instance
    }

    fun getSortFilterListHabit(typeHabit:TypeHabit?, sortType: SortType?, text:String?):LiveData<List<Habit>>{
        return if (typeHabit==null){
            instance.habitDao().getAllItems()
        }else
            when (sortType){
                SortType.AZ->{
                    instance.habitDao().getSortFilterHabitASC(typeHabit.str,text?:"")}
                SortType.ZA->{
                    instance.habitDao().getSortFilterHabitDESC(typeHabit.str,text?:"")}
                else -> {instance.habitDao().getFilterHabit(typeHabit.str,text?:"")}}
       }

    fun getHabitById(id: Long): Habit {
        return instance.habitDao().getHabitById(id)
    }

    fun addHabit(habit: Habit) {
        instance.habitDao().insertHabit(habit)
    }

    fun editHabitList(newHabit: Habit) {
        instance.habitDao().updateHabit(newHabit)
    }
}