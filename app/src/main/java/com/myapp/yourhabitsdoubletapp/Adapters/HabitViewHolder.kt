package com.myapp.yourhabitsdoubletapp.Adapters


import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.Habit
import com.myapp.yourhabitsdoubletapp.databinding.ItemHabitBinding

class HabitViewHolder(
    view: ItemHabitBinding,
    private val onItemClick: ((Habit, Int) -> Unit)?
) : RecyclerView.ViewHolder(view.root) {


    private val itemHabitLayout = view.itemHabit
    private val nameHabitText = view.nameHabit
    private val typeHabitText = view.typeHabit
    private val descriptionHabitText = view.descriptionHabit
    private val priorityHabitText = view.priorityHabit
    private val frequencyHabitText = view.frequencyHabit

    fun bind(habit: Habit) {
        nameHabitText.text = habit.nameHabit
        when (habit.typeHabit) {
            TypeHabit.NEGATIVE -> {
                typeHabitText.text = "Плохая привычка"
            }
            TypeHabit.POSITIVE -> {
                typeHabitText.text = "Хорошая привычка"
            }
        }
        descriptionHabitText.text = habit.descriptionHabit
        priorityHabitText.text = habit.priorityHabit.str
        frequencyHabitText.text = habit.numberExecutions.toString() + " " + habit.periodText
        itemHabitLayout.background = habit.colorHabit.toColor().toDrawable()
        itemHabitLayout.setOnClickListener {
            onItemClick?.invoke(habit, absoluteAdapterPosition)
        }
    }
}