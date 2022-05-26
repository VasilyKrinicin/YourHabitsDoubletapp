package com.myapp.domain.usecase

import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.SortType
import com.myapp.domain.repository.RepositoryHabit
import kotlinx.coroutines.flow.Flow


class GetSortedFilterListHabit(
    private val repositoryHabit: RepositoryHabit
) {
     fun execute(sortType: SortType?, textFilter: String?): Flow<List<HabitModel>> =
             repositoryHabit.getSortFilterListHabit(sortType, textFilter)
}