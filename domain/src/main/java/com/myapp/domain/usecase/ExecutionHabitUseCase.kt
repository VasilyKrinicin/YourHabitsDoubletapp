package com.myapp.domain.usecase

import android.util.Log
import android.widget.Toast
import com.myapp.domain.model.HabitDoneModel
import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.TypeHabit
import com.myapp.domain.repository.HabitRepository
import com.myapp.domain.repository.NetworkRepository
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*

class ExecutionHabitUseCase(
    private val habitRepository: HabitRepository,
    private val dispatcher: CoroutineDispatcher,
    private val networkRepository: NetworkRepository

) {
    suspend fun execute(habitModel: HabitModel): String {
        return withContext(dispatcher) {
            var text = ""
            val done = HabitDoneModel(
                Date().time.toInt(),
                habitModel.uid
            )
            habitModel.doneDate.add(done.date)
            habitRepository.editHabit(habitModel)
            text = if (habitModel.typeHabit == TypeHabit.POSITIVE) {
                if (habitModel.doneDate.size <= habitModel.numberExecutions)
                    "Стоит выпонить еще ${habitModel.numberExecutions - (habitModel.doneDate.size)} раз"
                else
                    "You are breathtaking"
            } else {
                if (habitModel.doneDate.size < habitModel.numberExecutions)
                    "Можно выпонить еще ${habitModel.numberExecutions - (habitModel.doneDate.size )} раз"
                else
                    "Хватит это делать!"
            }
            try {
                networkRepository.postHabit(done)
            } catch (e: Exception) {
                Log.e("Error add Done date", "error $e")
            }
            return@withContext text

        }
    }
}
