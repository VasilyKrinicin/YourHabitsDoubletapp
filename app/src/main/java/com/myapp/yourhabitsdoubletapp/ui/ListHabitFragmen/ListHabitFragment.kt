package com.myapp.yourhabitsdoubletapp.ui.ListHabitFragmen

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.domain.model.HabitModel
import com.myapp.yourhabitsdoubletapp.*
import com.myapp.yourhabitsdoubletapp.Adapters.AdapterHabit
import com.myapp.domain.model.TypeHabit
import com.myapp.yourhabitsdoubletapp.databinding.FragmentListHabitBinding
import com.myapp.yourhabitsdoubletapp.di.ListHabitComponent
import com.myapp.yourhabitsdoubletapp.di.components
import com.myapp.yourhabitsdoubletapp.ui.ViewPagerFragmentDirections
import kotlinx.coroutines.*
import javax.inject.Inject

class ListHabitFragment() : Fragment(R.layout.fragment_list_habit) {

    private var fragmentMainBinding: FragmentListHabitBinding? = null
    private var adapterHabit: AdapterHabit? = null
    private lateinit var typeHabit: TypeHabit
    private lateinit var text:String
    private val listHabitComponent: ListHabitComponent by components {
        (activity?.application as App).appComponent.listHabitComponent()
            .create()
    }

    @Inject
    lateinit var listHabitViewModel: ListHabitViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listHabitComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentListHabitBinding.bind(view)
        fragmentMainBinding = binding
        typeHabit = arguments?.getSerializable(HABIT_STATE) as TypeHabit
        observeViewModelState()
        initList()
    }

    //Инициализируем адаптер списка Habit
    private fun initList() {
        adapterHabit =
            AdapterHabit(
                ::modifyHabit,
                ::deleteHabit,
                ::executeHabit
            )

        fragmentMainBinding?.apply {
            with(habitList) {
                adapter = adapterHabit
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun executeHabit(habit: HabitModel) {
        GlobalScope.launch  (Dispatchers.IO) {
            listHabitViewModel.executeHabit(habit)
        }

    }


    private fun deleteHabit(habit: HabitModel) {
        listHabitViewModel.deleteHabit(habit)
    }

    private fun modifyHabit(habit: HabitModel) {
        val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToEditHabitFragment(
            uid = habit.uid
        )
        findNavController().navigate(direction)
    }


    private fun observeViewModelState() {
        listHabitViewModel.habitLiveData
            .observe(viewLifecycleOwner) { list ->
                adapterHabit?.items = list.filter { it.typeHabit == typeHabit }
            }
        listHabitViewModel.getHabitSortType().observe(viewLifecycleOwner) {
            listHabitViewModel.getSort()
        }

        listHabitViewModel.getHabitTextFilter().observe(viewLifecycleOwner) {
            listHabitViewModel.getSort()
        }
        listHabitViewModel.textMassageLiveData
            .observe(viewLifecycleOwner) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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