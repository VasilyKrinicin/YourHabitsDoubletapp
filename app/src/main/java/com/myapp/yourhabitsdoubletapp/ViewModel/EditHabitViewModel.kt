package com.myapp.yourhabitsdoubletapp.ViewModel

import androidx.lifecycle.ViewModel
import com.myapp.yourhabitsdoubletapp.Habit

class EditHabitViewModel : ViewModel() {


    fun fieldProcess(newHabit: Habit, oldHabit: Habit?) {
        if (oldHabit != null && oldHabit.id == newHabit.id) {
            HabitRepository.editHabitList(newHabit)
        } else {
            HabitRepository.addHabit(newHabit)
        }
    }
}