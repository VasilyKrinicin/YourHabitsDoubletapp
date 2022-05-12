package com.myapp.yourhabitsdoubletapp.ui.EditHabitFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.yourhabitsdoubletapp.Networking.NetworkRepository
import com.myapp.yourhabitsdoubletapp.Networking.UID
import com.myapp.yourhabitsdoubletapp.database.Habit
import com.myapp.yourhabitsdoubletapp.database.HabitRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class EditHabitViewModel : ViewModel(),
    CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler { _, e -> throw e }

    private val getHabitMutableLiveData = MutableLiveData<Habit>()

    val getHabitLiveData: LiveData<Habit>
        get() = getHabitMutableLiveData

    fun init(id: Long) = launch(Dispatchers.IO) {
        try {
            val habit = HabitRepository.getHabitById(id)
            getHabitMutableLiveData.postValue(habit)
        } catch (t: Throwable) {

        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun fieldProcess(newHabit: Habit) {
        if (newHabit.id == 0L) {
            NetworkRepository.networkApi.putHabit(newHabit.toHabitNetwork()).enqueue(
                object : Callback<UID> {
                    override fun onResponse(call: Call<UID>, response: Response<UID>) {
                        if (response.isSuccessful) {
                            newHabit.uid = response.body()?.uid
                            newHabit.unloaded = true
                            launch(Dispatchers.IO) { HabitRepository.addHabit(newHabit) }
                        } else {
                            Log.e("errorResponse", "Error1")
                            newHabit.uid = null
                            newHabit.unloaded = false
                            launch(Dispatchers.IO) { HabitRepository.addHabit(newHabit) }
                        }
                    }

                    override fun onFailure(call: Call<UID>, t: Throwable) {
                        Log.e("errorResponse", "Error2")
                        newHabit.uid = null
                        newHabit.unloaded = false
                        launch(Dispatchers.IO) { HabitRepository.addHabit(newHabit) }
                    }

                }
            )
        } else {
            NetworkRepository.networkApi.putHabit(newHabit.toHabitNetwork()).enqueue(
                object : Callback<UID> {
                    override fun onResponse(call: Call<UID>, response: Response<UID>) {
                        if (response.isSuccessful) {
                            newHabit.uid = response.body()?.uid
                            newHabit.unloaded = true
                            launch(Dispatchers.IO) { HabitRepository.editHabit(newHabit) }
                        } else {
                            Log.e("errorResponse", "Error3")
                            newHabit.uid = null
                            newHabit.unloaded = false
                            launch(Dispatchers.IO) { HabitRepository.editHabit(newHabit) }
                        }
                    }

                    override fun onFailure(call: Call<UID>, t: Throwable) {
                        Log.e("errorResponse", "Error4")
                        newHabit.uid = null
                        newHabit.unloaded = false
                        launch(Dispatchers.IO) { HabitRepository.editHabit(newHabit) }
                    }

                }
            )
        }
    }


}