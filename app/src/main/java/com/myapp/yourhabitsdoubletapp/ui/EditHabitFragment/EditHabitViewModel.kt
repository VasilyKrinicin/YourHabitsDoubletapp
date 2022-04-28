package com.myapp.yourhabitsdoubletapp.ui.EditHabitFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.yourhabitsdoubletapp.database.Habit
import com.myapp.yourhabitsdoubletapp.database.HabitRepository
import kotlinx.coroutines.launch

class EditHabitViewModel : ViewModel() {
    private val getHabitMutableLiveData = MutableLiveData<Habit>()

    val getHabitLiveData: LiveData<Habit>
        get() = getHabitMutableLiveData

    fun init(id: Long) {
        viewModelScope.launch {
            try {
                val habit = HabitRepository.getHabitById(id)
                getHabitMutableLiveData.postValue(habit)
            } catch (t: Throwable) {
                throw t
            }
        }
    }

    fun fieldProcess(newHabit: Habit) {
            viewModelScope.launch {
                try {
                    if (newHabit.id == 0L) {
                        HabitRepository.addHabit(newHabit)
                    } else {
                        HabitRepository.editHabitList(newHabit)
                    }

                } catch (t: Throwable) {
                    throw (t)

                }
        }
    }
}