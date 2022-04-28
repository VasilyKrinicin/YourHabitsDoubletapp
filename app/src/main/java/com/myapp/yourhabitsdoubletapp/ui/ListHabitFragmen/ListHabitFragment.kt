package com.myapp.yourhabitsdoubletapp.ui.ListHabitFragmen

import android.os.Bundle
import android.view.View

import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.yourhabitsdoubletapp.*
import com.myapp.yourhabitsdoubletapp.Adapters.AdapterHabit
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.database.Habit
import com.myapp.yourhabitsdoubletapp.databinding.FragmentListHabitBinding
import com.myapp.yourhabitsdoubletapp.ui.ViewPagerFragmentDirections

class ListHabitFragment() : Fragment(R.layout.fragment_list_habit) {

    private var fragmentMainBinding: FragmentListHabitBinding? = null
    private var adapterHabit: AdapterHabit? = null
    private val listHabitViewModel: ListHabitViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentListHabitBinding.bind(view)
        fragmentMainBinding = binding
        val typeHabit = arguments?.getSerializable(HABIT_STATE) as TypeHabit
        listHabitViewModel.setHabitTypeFilter(typeHabit)
        observeViewModelState()
        initList()
    }

    override fun onResume(){
        val typeHabit = arguments?.getSerializable(HABIT_STATE) as TypeHabit
        listHabitViewModel.setHabitTypeFilter(typeHabit)
        super.onResume()
    }

    //Инициализируем адаптер списка Habit
    private fun initList() {
        adapterHabit =
            AdapterHabit (
                    ::modifyHabit
                )

        fragmentMainBinding?.apply {
            with(habitList) {
                adapter = adapterHabit
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }


    private fun modifyHabit(habit: Habit) {
        val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToEditHabitFragment(habit.id)
        findNavController().navigate(direction)
    }


    private fun observeViewModelState() {
        listHabitViewModel.habitLiveData
            .observe(viewLifecycleOwner) {
                adapterHabit?.items = it
            }
        listHabitViewModel.getHabitSortType().observe(viewLifecycleOwner) {
           listHabitViewModel.getSort()
        }
        listHabitViewModel.getHabitTypeFilter().observe(viewLifecycleOwner) {
            listHabitViewModel.getSort()
        }
        listHabitViewModel.getHabitTextFilter().observe(viewLifecycleOwner) {
            listHabitViewModel.getSort()
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