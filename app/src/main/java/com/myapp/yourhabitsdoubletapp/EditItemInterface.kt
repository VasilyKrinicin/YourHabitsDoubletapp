package com.myapp.yourhabitsdoubletapp

import com.myapp.yourhabitsdoubletapp.Data.PriorityHabit
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit

interface EditItemInterface {
    fun editItem(habit: Habit)
    fun newItem(habit: Habit)
}
