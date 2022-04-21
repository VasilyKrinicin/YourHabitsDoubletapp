package com.myapp.yourhabitsdoubletapp.ui

import android.os.Bundle
import android.view.View

import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.yourhabitsdoubletapp.*
import com.myapp.yourhabitsdoubletapp.Adapters.AdapterHabit
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.ViewModel.HabitListViewModel
import com.myapp.yourhabitsdoubletapp.database.Habit
import com.myapp.yourhabitsdoubletapp.databinding.FragmentListHabitBinding

class ListHabitFragment() : Fragment(R.layout.fragment_list_habit) {

    private var fragmentMainBinding: FragmentListHabitBinding? = null
    private var adapterHabit: AdapterHabit? = null
    private val habitListViewModel: HabitListViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentListHabitBinding.bind(view)
        fragmentMainBinding = binding
        val typeHabit = arguments?.getSerializable(HABIT_STATE) as TypeHabit
        habitListViewModel.setHabitTypeFilter(typeHabit)
        observeViewModelState()
        initList()
    }

    override fun onResume() {
        super.onResume()
        val typeHabit = arguments?.getSerializable(HABIT_STATE) as TypeHabit
        habitListViewModel.setHabitTypeFilter(typeHabit)

    }

    //Инициализируем адаптер списка Habit
    private fun initList() {
        adapterHabit =
            AdapterHabit() { habit, _ ->
                modifyHabit(habitListViewModel.getHabitById(habit.id))
            }
        fragmentMainBinding?.apply {
            with(habitList) {
                adapter = adapterHabit
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun modifyHabit(habit: Habit) {
        val bundle = Bundle()
        bundle.putLong(EDIT_HABIT, habit.id)
        findNavController().navigate(R.id.dialogHabitFragment, bundle)
    }

    private fun observeViewModelState() {
        habitListViewModel.habitLiveData
            .observe(viewLifecycleOwner) {
                adapterHabit?.items = it
            }
        habitListViewModel.getHabitSortType().observe(viewLifecycleOwner)
        {
            habitListViewModel.getSort()
        }
        habitListViewModel.getHabitTypeFilter().observe(viewLifecycleOwner) {
            habitListViewModel.getSort()
        }
        habitListViewModel.getHabitTextFilter().observe(viewLifecycleOwner) {
            habitListViewModel.getSort()
        }


    }

    companion object {
        private const val EDIT_HABIT = "edit_habit"
        private const val HABIT_STATE = "HabitState"

        fun newInstance(
            habit: TypeHabit
        ): ListHabitFragment {
            return ListHabitFragment().withArguments {
                putSerializable(HABIT_STATE, habit)
            }
        }
    }

    override fun onDestroy() {
        fragmentMainBinding = null
        super.onDestroy()
    }
}