package com.myapp.domain.usecase

import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.SortType
import com.myapp.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow


class GetSortedFilterListHabitUseCase(
    private val habitRepository: HabitRepository
) {
     fun execute(sortType: SortType?, textFilter: String?): Flow<List<HabitModel>> =
             habitRepository.getSortFilterListHabit(sortType, textFilter)
}