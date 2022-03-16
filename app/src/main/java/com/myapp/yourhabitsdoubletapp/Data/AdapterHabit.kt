package com.myapp.yourhabitsdoubletapp.Data


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.yourhabitsdoubletapp.Habit
import com.myapp.yourhabitsdoubletapp.R

class AdapterHabit (
    private var habit: ArrayList<Habit>,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater =LayoutInflater.from(parent.context)
        return HabitViewHolder(inflater.inflate(R.layout.item_habit,parent,false),onItemClick)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
           is HabitViewHolder-> {
                holder.bind(habit[position])
            }
        }
    }

    override fun getItemCount(): Int =habit.size

    //обновление отредактированого элемента в списке Habit поиск по ID
    fun editHabitList(editHabit: Habit){
        habit.forEachIndexed { index, it ->
            if (it.id==editHabit.id) {
                habit[index]=editHabit
                notifyItemChanged(index)

            }
        }
    }
    //Добвление нового элемена
    fun addHabit(newHabit: Habit){
        habit.add(newHabit)
        notifyItemChanged(habit.size)
    }

}