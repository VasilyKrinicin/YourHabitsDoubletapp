package com.myapp.domain.usecase

import com.myapp.domain.repository.RepositoryHabit
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetHabitByUid(
    private val repositoryHabit: RepositoryHabit
) {
      fun execute(uid: String) =
            repositoryHabit.getHabitByUID(uid = uid)

}
