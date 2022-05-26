package com.myapp.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME}")
    fun getAllItems(): Flow<List<Habit>>

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.UID} LIKE :uid")
    fun getHabitByUID(uid: String): Flow<Habit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.TYPE_HABIT} LIKE :typeHabit")
    fun getHabitByType(typeHabit: String): Flow<List<Habit>>

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.NAME_HABIT} LIKE '%'||:text||'%' ORDER BY ${HabitContract.Columns.NAME_HABIT} DESC")
    fun getSortFilterHabitDESC(text: String): Flow<List<Habit>>

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.NAME_HABIT} LIKE '%'||:text||'%'")
    fun getFilterHabit(text: String): Flow<List<Habit>>

    @Query("SELECT * FROM ${HabitContract.TABLE_NAME} WHERE ${HabitContract.Columns.NAME_HABIT} LIKE '%'||:text||'%' ORDER BY ${HabitContract.Columns.NAME_HABIT} ASC")
    fun getSortFilterHabitASC(text: String): Flow<List<Habit>>


}