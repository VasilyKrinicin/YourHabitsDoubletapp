package com.myapp.domain.usecase

import com.myapp.domain.model.HabitModel
import com.myapp.domain.repository.RepositoryHabit
import kotlinx.coroutines.flow.Flow


class GetHabitList(
    private val repositoryHabit: RepositoryHabit

) {
   fun execute(): Flow<List<HabitModel>> =
             repositoryHabit.getAll()

}