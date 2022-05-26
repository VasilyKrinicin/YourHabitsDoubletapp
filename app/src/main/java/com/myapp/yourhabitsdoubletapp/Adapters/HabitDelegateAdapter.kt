package com.myapp.yourhabitsdoubletapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.myapp.data.database.Habit
import com.myapp.domain.model.HabitModel
import com.myapp.yourhabitsdoubletapp.databinding.ItemHabitBinding

class HabitDelegateAdapter(
    private val onItemClick: (habit: HabitModel) -> Unit,
    private val deleteHabit: (habit: HabitModel) -> Unit,
    private val executeHabit: (habit: HabitModel) -> Unit
) :
    AbsListItemAdapterDelegate<HabitModel, HabitModel, HabitViewHolder>() {


    override fun isForViewType(
        item: HabitModel,
        items: MutableList<HabitModel>,
        position: Int
    ): Boolean {
        return item is HabitModel
    }

    override fun onCreateViewHolder(parent: ViewGroup): HabitViewHolder {
        val itemBinding =
            ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HabitViewHolder(itemBinding, onItemClick, deleteHabit, executeHabit)
    }

    override fun onBindViewHolder(
        item: HabitModel,
        holder: HabitViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}