package com.myapp.yourhabitsdoubletapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME}")
    fun getAllItems(): LiveData<List<Habit>>

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.ID} LIKE :id LIMIT 1 ")
    fun getHabitById(id: Long): Habit

    @Insert
    fun insertHabit(habit: Habit)

    @Update
    fun updateHabit(habit: Habit)

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.TYPE_HABIT} LIKE :typeHabit")
    fun getHabitByType(typeHabit: String): LiveData<List<Habit>>

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.TYPE_HABIT} LIKE :typeHabit AND ${HabitContract.Columns.NAME_HABIT} LIKE '%'||:text||'%' ORDER BY ${HabitContract.Columns.NAME_HABIT} DESC")
    fun getSortFilterHabitDESC(typeHabit: String, text: String): LiveData<List<Habit>>

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.TYPE_HABIT} LIKE :typeHabit AND ${HabitContract.Columns.NAME_HABIT} LIKE '%'||:text||'%'")
    fun getFilterHabit(typeHabit: String, text: String): LiveData<List<Habit>>

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.TYPE_HABIT} LIKE :typeHabit AND ${HabitContract.Columns.NAME_HABIT} LIKE '%'||:text||'%' ORDER BY ${HabitContract.Columns.NAME_HABIT} ASC")
    fun getSortFilterHabitASC(typeHabit: String, text: String): LiveData<List<Habit>>


}