package com.myapp.domain.usecase

import com.myapp.domain.model.HabitModel
import com.myapp.domain.repository.RepositoryHabit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteHabit(
    private val repositoryHabit: RepositoryHabit,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun execute(habitModel: HabitModel) {
        withContext(dispatcher) {
            repositoryHabit.deleteHabit(habitModel)
        }
    }
}