package com.myapp.domain.usecase

import com.myapp.domain.model.HabitModel
import com.myapp.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow


class GetHabitListUseCase(
    private val habitRepository: HabitRepository

) {
   fun execute(): Flow<List<HabitModel>> =
             habitRepository.getAll()

}