package com.myapp.yourhabitsdoubletapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.myapp.yourhabitsdoubletapp.Data.SortType
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.R
import com.myapp.yourhabitsdoubletapp.ViewModel.HabitListViewModel
import com.myapp.yourhabitsdoubletapp.ViewModel.HabitRepository
import com.myapp.yourhabitsdoubletapp.databinding.FragmentBottomSheetBinding

class BottomSheetFragment() : Fragment(R.layout.fragment_bottom_sheet) {
    private val listHabitRepository = HabitRepository
    private var fragmentBottomSheetBinding: FragmentBottomSheetBinding? = null
    private val habitListViewModel: HabitListViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBottomSheetBinding.bind(view)
        fragmentBottomSheetBinding = binding
        initSpiner()
        filterList()
        binding.spinerSorted.setOnItemClickListener { _, _, _, _ ->
            habitListViewModel.sortedAndFiltered(
                listHabitRepository.getHabitList(),
                getSortedType(),
                binding.filterNameText.text.toString()
            )
        }
    }

    override fun onResume() {
        initSpiner()
        super.onResume()
    }

    private fun filterList() {
        val textChangedListenerAdd = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                habitListViewModel.sortedAndFiltered(
                    listHabitRepository.getHabitList(),
                    getSortedType(),
                    s?.toString()
                )
            }
        }
        fragmentBottomSheetBinding?.filterNameLayout?.editText?.addTextChangedListener(
            textChangedListenerAdd
        )
    }

    override fun onDestroy() {
        fragmentBottomSheetBinding = null
        super.onDestroy()
    }

    private fun initSpiner() {
        val sortList = arrayListOf<String>(
            SortType.AZ.str, SortType.ZA.str, SortType.NONE.str
        )

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spiner,
            sortList
        )
        fragmentBottomSheetBinding?.spinerSorted?.setAdapter(adapter)

    }

    fun getSortedType(): SortType {
        var sortType = SortType.NONE

        when (fragmentBottomSheetBinding?.spinerSorted?.text.toString()) {
            SortType.AZ.str -> {
                sortType = SortType.AZ
            }
            SortType.ZA.str -> {
                sortType = SortType.ZA
            }
            SortType.NONE.str -> {
                sortType = SortType.NONE
            }
        }
        return sortType
    }
}
