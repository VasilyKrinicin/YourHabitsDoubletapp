package com.myapp.domain.usecase

import com.myapp.domain.model.HabitModel
import com.myapp.domain.repository.RepositoryHabit
import kotlinx.coroutines.*

class ExecutionHabit(
    private val repositoryHabit: RepositoryHabit,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun execute(habitModel: HabitModel) {
        withContext(dispatcher) {
            repositoryHabit.executionHabit(habitModel)
        }
    }
}
