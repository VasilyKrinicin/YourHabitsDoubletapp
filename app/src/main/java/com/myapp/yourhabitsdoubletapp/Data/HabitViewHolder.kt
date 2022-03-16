package com.myapp.yourhabitsdoubletapp.Data

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.myapp.yourhabitsdoubletapp.Habit
import com.myapp.yourhabitsdoubletapp.R
import com.myapp.yourhabitsdoubletapp.TypeHabit

class HabitViewHolder(
    view: View,
    onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.setOnClickListener {
            onItemClick(absoluteAdapterPosition)
        }
    }
private val itemHabitLayout=view.findViewById<ConstraintLayout>(R.id.item_habit)
    private val nameHabitText=view.findViewById<TextView>(R.id.nameHabit)
    private val typeHabitText=view.findViewById<TextView>(R.id.typeHabit)
    private val descriptionHabitText=view.findViewById<TextView>(R.id.descriptionHabit)
    private val priorityHabitText=view.findViewById<TextView>(R.id.priorityHabit)
    private val frequencyHabitText=view.findViewById<TextView>(R.id.frequencyHabit)

    fun bind(habit: Habit){
        nameHabitText.text=habit.nameHabit
        when (habit.typeHabit){
            TypeHabit.NEGATIVE ->{
                typeHabitText.text="Плохая привычка"
            }
            TypeHabit.POSITIVE ->{
                typeHabitText.text="Хорошая привычка"
            }
        }
        descriptionHabitText.text=habit.descriptionHabit
        priorityHabitText.text=habit.priorityHabit.str
        frequencyHabitText.text=habit.numberExecutions.toString()+ " "+ habit.periodText
        itemHabitLayout.background=habit.colorHabit.toColor().toDrawable()

    }
}