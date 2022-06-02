package com.myapp.domain.repository

import com.myapp.domain.model.HabitDoneModel
import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.UidModel

interface NetworkRepository {

    fun synchronizeHabit()

    suspend fun putHabit(newHabit: HabitModel): String

    suspend fun deleteHabitNetwork(uidModel: UidModel)

    suspend fun postHabit(doneModel: HabitDoneModel)

    fun loadToServer()

    fun loadToDb()
}