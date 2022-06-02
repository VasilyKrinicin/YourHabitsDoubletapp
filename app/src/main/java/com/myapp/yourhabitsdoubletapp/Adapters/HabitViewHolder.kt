package com.myapp.yourhabitsdoubletapp.Adapters


import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.TypeHabit
import com.myapp.yourhabitsdoubletapp.databinding.ItemHabitBinding

class HabitViewHolder(
    view: ItemHabitBinding,
    private val onItemClick: ((HabitModel) -> Unit)?,
    private val deleteHabit: ((HabitModel) -> Unit)?,
    private val executeHabit: ((HabitModel) -> Unit)?
) : RecyclerView.ViewHolder(view.root) {


    private val itemHabitLayout = view.itemHabit
    private val nameHabitText = view.nameHabit
    private val typeHabitText = view.typeHabit
    private val descriptionHabitText = view.descriptionHabit
    private val priorityHabitText = view.priorityHabit
    private val frequencyHabitText = view.frequencyHabit
    private val buttonDone = view.doneButton
    private val countExecuteTextView=view.countExecute

    fun bind(habit: HabitModel) {
        nameHabitText.text = habit.nameHabit
        when (habit.typeHabit) {
            TypeHabit.NEGATIVE -> {
                typeHabitText.text = "Плохая привычка"
            }
            TypeHabit.POSITIVE -> {
                typeHabitText.text = "Хорошая привычка"
            }
        }
        countExecuteTextView.text= "Текущее количество выполнений ${habit.doneDate.size}"
        descriptionHabitText.text = habit.descriptionHabit
        priorityHabitText.text = habit.priorityHabit.str
        frequencyHabitText.text = "Количество ${habit.numberExecutions}, период в день ${habit.periodText}"
        itemHabitLayout.background = habit.colorHabit.toColor().toDrawable()
        buttonDone.setOnClickListener {
            executeHabit?.invoke(habit)
        }
        itemHabitLayout.setOnClickListener {
            onItemClick?.invoke(habit)
        }
        itemHabitLayout.setOnLongClickListener {
            deleteHabit?.invoke(habit)
            true
        }
    }
}