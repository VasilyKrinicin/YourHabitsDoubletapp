package com.myapp.yourhabitsdoubletapp.Adapters


import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.myapp.domain.model.HabitModel

class AdapterHabit(
    onItemClick: (habit: HabitModel) -> Unit,
    deleteHabit: (habit: HabitModel) -> Unit,
    executeHabit: (habit: HabitModel) -> Unit
) : AsyncListDifferDelegationAdapter<HabitModel>(HabitDiffutilCallBack()) {
    init {
        delegatesManager.addDelegate(HabitDelegateAdapter(onItemClick, deleteHabit, executeHabit))
    }

    class HabitDiffutilCallBack() : DiffUtil.ItemCallback<HabitModel>() {
        override fun areItemsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
            return oldItem == newItem
        }
    }
}