package com.myapp.yourhabitsdoubletapp.Adapters


import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.myapp.yourhabitsdoubletapp.Habit

class AdapterHabit(
    onItemClick: (habit: Habit, position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Habit>(HabitDiffutilCallBack()) {
    init {
        delegatesManager.addDelegate(HabitDelegateAdapter(onItemClick))
    }

    class HabitDiffutilCallBack() : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }
    }
}