package com.myapp.domain.repository

import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.SortType
import kotlinx.coroutines.flow.Flow

interface RepositoryHabit {

    fun getAll(): Flow<List<HabitModel>>

    fun getSortFilterListHabit(
        sortType: SortType?,
        text: String?
    ): Flow<List<HabitModel>>

    fun getHabitByUID(uid: String): Flow<HabitModel>

    suspend fun addHabit(habit: HabitModel)

    suspend fun editHabit(habit: HabitModel)

    suspend fun deleteHabit(habit: HabitModel)

    suspend fun executionHabit(habitModel: HabitModel)

}