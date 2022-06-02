package com.myapp.domain.usecase

import com.myapp.domain.repository.HabitRepository

class GetHabitByUidUseCase(
    private val habitRepository: HabitRepository
) {
      fun execute(uid: String) =
            habitRepository.getHabitByUID(uid = uid)

}
