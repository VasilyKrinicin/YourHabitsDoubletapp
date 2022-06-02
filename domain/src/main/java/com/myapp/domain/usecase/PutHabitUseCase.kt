package com.myapp.domain.usecase

import com.myapp.domain.model.HabitModel
import com.myapp.domain.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class PutHabitUseCase(private val networkRepository: NetworkRepository,
                      private val dispatcher: CoroutineDispatcher
)  {
    suspend fun execute(newHabit: HabitModel):String= coroutineScope {
        withContext(dispatcher){
            networkRepository.putHabit(newHabit)
        }
    }
}