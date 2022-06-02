package com.myapp.domain.usecase

import android.util.Log
import com.myapp.domain.model.HabitModel
import com.myapp.domain.repository.HabitRepository
import com.myapp.domain.repository.NetworkRepository
import kotlinx.coroutines.*
import java.lang.Exception

class AddOrUpdateUseCase(
    private val dispatcher: CoroutineDispatcher,
    private val putHabitUseCase: PutHabitUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase
) {

    suspend fun execute(newHabit: HabitModel, customUid: String) = coroutineScope {
        withContext(dispatcher)
        {
            if (newHabit.uid == customUid || newHabit.uid == "") {
                try {
                    newHabit.uid=""
                    newHabit.uid = putHabitUseCase.execute(newHabit)
                    newHabit.unloaded = true

                } catch (e: Exception) {

                    newHabit.uid = this.hashCode().toString()
                    newHabit.unloaded = false
                    Log.e("Error TryCatch", "Error add habit", e)
                } finally {
                    addHabitUseCase.execute(newHabit)
                }
            } else {
                try {
                    newHabit.uid=""
                    newHabit.uid = putHabitUseCase.execute(newHabit)
                    newHabit.unloaded = true
                } catch (e: Exception) {
                    // newHabit.uid = CUSTOM_UID
                    newHabit.unloaded = false
                    Log.e("Error TryCatch", "Error update habit", e)
                } finally {
                    updateHabitUseCase.execute(newHabit)
                }
            }
        }
    }
}



