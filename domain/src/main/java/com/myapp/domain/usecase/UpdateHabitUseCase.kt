package com.myapp.domain.usecase

import com.myapp.domain.model.HabitModel
import com.myapp.domain.repository.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UpdateHabitUseCase(
    private val habitRepository: HabitRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun execute(habitModel: HabitModel) {
        withContext(dispatcher) {
            habitRepository.editHabit(habitModel)
        }
    }
}