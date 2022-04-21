package com.myapp.yourhabitsdoubletapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.myapp.yourhabitsdoubletapp.database.Habit
import com.myapp.yourhabitsdoubletapp.databinding.ItemHabitBinding

class HabitDelegateAdapter(private val onItemClick: (habit: Habit, position: Int) -> Unit) :
    AbsListItemAdapterDelegate<Habit, Habit, HabitViewHolder>() {


    override fun isForViewType(item: Habit, items: MutableList<Habit>, position: Int): Boolean {
        return item is Habit
    }

    override fun onCreateViewHolder(parent: ViewGroup): HabitViewHolder {
        val itemBinding =
            ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(itemBinding, onItemClick)
    }

    override fun onBindViewHolder(
        item: Habit,
        holder: HabitViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}