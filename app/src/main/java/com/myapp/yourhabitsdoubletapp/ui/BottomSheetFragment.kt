package com.myapp.yourhabitsdoubletapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.myapp.yourhabitsdoubletapp.Data.SortType
import com.myapp.yourhabitsdoubletapp.R
import com.myapp.yourhabitsdoubletapp.ui.ListHabitFragmen.ListHabitViewModel
import com.myapp.yourhabitsdoubletapp.databinding.FragmentBottomSheetBinding

class BottomSheetFragment() : Fragment(R.layout.fragment_bottom_sheet) {

    private var fragmentBottomSheetBinding: FragmentBottomSheetBinding? = null
    private val listHabitViewModel: ListHabitViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBottomSheetBinding.bind(view)
        fragmentBottomSheetBinding = binding
        initSpiner()
        filterList()
        binding.spinerSorted.setOnItemClickListener { _, _, _, _ ->
            listHabitViewModel.putSortType(getSortedType())
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
                listHabitViewModel.setTextFilter(s.toString())
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
        val sortList = arrayListOf(
            SortType.AZ.str, SortType.ZA.str, SortType.NONE.str
        )

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spiner,
            sortList
        )
        fragmentBottomSheetBinding?.spinerSorted?.setAdapter(adapter)
    }

    private fun getSortedType(): SortType {
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
