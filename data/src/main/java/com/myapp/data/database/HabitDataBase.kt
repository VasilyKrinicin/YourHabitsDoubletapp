package com.myapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

import com.myapp.data.database.HabitDataBase.Companion.DB_VERSION

@Database(entities = [Habit::class], version = DB_VERSION)
abstract class HabitDataBase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "habit-database"
    }
}

