package com.myapp.yourhabitsdoubletapp.Adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.Habit
import com.myapp.yourhabitsdoubletapp.ui.ListFragmentHabit


class TabPagerAdapter(
    habitListIn: ArrayList<Habit>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {
    private val habitPositive = arrayListOf<Habit>()
    private val habitNegative = arrayListOf<Habit>()
    private var negativeHabitFragmentHabit: ListFragmentHabit
    private var positiveHabitFragmentHabit: ListFragmentHabit
    private var habitList = habitListIn

    init {
        habitListIn.forEach { it ->
            when (it.typeHabit) {
                TypeHabit.NEGATIVE -> {
                    habitNegative.add(it)
                }
                TypeHabit.POSITIVE -> {
                    habitPositive.add(it)
                }
            }
        }
        negativeHabitFragmentHabit = ListFragmentHabit.newInstance(habitNegative)
        positiveHabitFragmentHabit = ListFragmentHabit.newInstance(habitPositive)
    }

    private val typeHabit = listOf<TypeHabit>(
        TypeHabit.POSITIVE,
        TypeHabit.NEGATIVE
    )


    override fun getItemCount(): Int {
        return typeHabit.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                positiveHabitFragmentHabit
            }
            else -> {
                negativeHabitFragmentHabit
            }
        }

    }

    fun addItem(habit: Habit) {
        when (habit.typeHabit) {
            TypeHabit.POSITIVE -> {
                positiveHabitFragmentHabit.addHabit(habit)

            }
            TypeHabit.NEGATIVE -> {
                negativeHabitFragmentHabit.addHabit(habit)

            }
        }
    }

    fun updateItem(habit: Habit) {
        habitList.forEachIndexed { index, it ->
            if (it.id == habit.id) {
                when (habit.typeHabit) {
                    TypeHabit.POSITIVE -> {
                        if (it.typeHabit == habit.typeHabit) {
                            positiveHabitFragmentHabit.updateHabit(habit)
                        } else {
                            positiveHabitFragmentHabit.removeHabit(habit)
                            negativeHabitFragmentHabit.addHabit(habit)
                        }
                    }
                    TypeHabit.NEGATIVE -> {
                        if (it.typeHabit == habit.typeHabit) {
                            negativeHabitFragmentHabit.updateHabit(habit)
                        } else {
                            negativeHabitFragmentHabit.removeHabit(habit)
                            positiveHabitFragmentHabit.addHabit(habit)
                        }
                    }
                }
            }
        }

    }
}