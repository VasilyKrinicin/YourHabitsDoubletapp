package com.myapp.data.database

import com.myapp.data.convert.ConvertModel
import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.SortType
import com.myapp.domain.repository.RepositoryHabit
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class HabitRepositoryImpl(
    private val habitDao: HabitDao
) : CoroutineScope, RepositoryHabit {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }



    override fun getAll(): Flow<List<HabitModel>> =
        habitDao.getAllItems().map { ConvertModel.convertToHabitModelList(it) }

    override fun getSortFilterListHabit(
        sortType: SortType?,
        text: String?
    ): Flow<List<HabitModel>> {
        return when (sortType) {
            SortType.AZ -> {
                habitDao.getSortFilterHabitASC(text ?: "")
                    .map { ConvertModel.convertToHabitModelList(it) }
            }
            SortType.ZA -> {
                habitDao.getSortFilterHabitDESC(text ?: "")
                    .map { ConvertModel.convertToHabitModelList(it) }
            }
            else -> {
                habitDao.getFilterHabit(text ?: "")
                    .map { ConvertModel.convertToHabitModelList(it) }
            }
        }
    }

    override fun getHabitByUID(uid: String): Flow<HabitModel> =
        habitDao.getHabitByUID(uid).map { ConvertModel.convertHabitToHabitModel(it)
    }

    override suspend fun addHabit(habitModel: HabitModel) {
        habitDao.insertHabit(ConvertModel.convertHabitModelToHabit(habitModel))
    }

    override suspend fun editHabit(habitModel: HabitModel) {
        habitDao.updateHabit(ConvertModel.convertHabitModelToHabit(habitModel))
    }

    override suspend fun deleteHabit(habitModel: HabitModel) {
        habitDao.deleteHabit(ConvertModel.convertHabitModelToHabit(habitModel))
    }

    override suspend fun executionHabit(habitModel: HabitModel) {

    }


}
