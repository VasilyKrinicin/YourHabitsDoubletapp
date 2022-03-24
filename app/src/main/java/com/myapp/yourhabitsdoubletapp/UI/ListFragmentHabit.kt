package com.myapp.yourhabitsdoubletapp.UI

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible

import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.yourhabitsdoubletapp.*
import com.myapp.yourhabitsdoubletapp.Adapters.AdapterHabit
import com.myapp.yourhabitsdoubletapp.databinding.FragmentListHabitBinding

import java.util.ArrayList

class ListFragmentHabit() : Fragment(R.layout.fragment_list_habit) {

    private var fragmentMainBinding: FragmentListHabitBinding? = null
    private var adapterHabit: AdapterHabit? = null
    private var habitsList: ArrayList<Habit> = arrayListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentListHabitBinding.bind(view)
        fragmentMainBinding = binding
        if (savedInstanceState != null) {
            habitsList = savedInstanceState.getParcelableArrayList(HABIT_STATE) ?: arrayListOf()
            initList(habitsList)
        }
        arguments?.let {

            habitsList = it.getParcelableArrayList(HABIT_STATE) ?: arrayListOf()
            initList(habitsList)
        }
        initList(habitsList)
        adapterHabit?.items = habitsList
        binding.textViewEmptyList.isVisible = habitsList.isEmpty()

    }

    //Инициализируем адаптер списка Habit
    private fun initList(habit: ArrayList<Habit>) {
        adapterHabit =
            AdapterHabit(habitsList) { position -> modifyHabit(habitsList[position]) }
        with(fragmentMainBinding?.habitList) {
            this?.adapter = adapterHabit
            this?.layoutManager = LinearLayoutManager(requireContext())
        }
        fragmentMainBinding?.textViewEmptyList?.isVisible = habit.isEmpty()
    }

    private fun modifyHabit(habit: Habit) {
        val fragment = DialogHabitFragment()
        val bundle = Bundle().apply { putParcelable(EDIT_HABIT, habit) }
        fragment.arguments = bundle
        fragment.show(parentFragmentManager, "dialog")
    }

    fun addHabit(habit: Habit) {
        adapterHabit?.addHabit(habit)
    }

    fun updateHabit(habit: Habit) {
        adapterHabit?.editHabitList(habit)
    }

    fun removeHabit(habit: Habit) {
        adapterHabit?.removeHabit(habit)
    }

    companion object {
        private const val EDIT_HABIT = "edit_habit"
        private const val HABIT_STATE = "HabitState"


        fun newInstance(
            habit: ArrayList<Habit>
        ): ListFragmentHabit {
            return ListFragmentHabit().withArguments {
                putParcelableArrayList(HABIT_STATE, habit)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(HABIT_STATE, "onSaveInstanceState")
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(HABIT_STATE, habitsList)
    }


    override fun onDestroy() {
        fragmentMainBinding = null
        super.onDestroy()
    }
}


/*







  */