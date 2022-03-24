package com.myapp.yourhabitsdoubletapp.Adapters


import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.myapp.yourhabitsdoubletapp.Habit

class AdapterHabit(
    habitListIn: ArrayList<Habit>,
    onItemClick: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Habit>(HabitDifutilCallBack()) {
    init {
        delegatesManager.addDelegate(HabitDelegateAdapter(onItemClick))

    }

    var habitList = habitListIn

    fun editHabitList(habit: Habit) {
        habitList.forEachIndexed { index, it ->
            if (it.id == habit.id) {
                habitList[index] = habit
            }
        }
    }

    fun addHabit(habit: Habit) {
        habitList.add(habit)
    }

    fun removeHabit(habit: Habit) {
        habitList.remove(habit)
    }

    class HabitDifutilCallBack() : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }

    }
}