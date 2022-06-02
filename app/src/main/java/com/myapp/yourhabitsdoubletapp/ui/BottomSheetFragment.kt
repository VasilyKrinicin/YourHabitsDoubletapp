package com.myapp.yourhabitsdoubletapp.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.myapp.domain.model.SortType
import com.myapp.yourhabitsdoubletapp.App
import com.myapp.yourhabitsdoubletapp.R
import com.myapp.yourhabitsdoubletapp.ui.ListHabitFragmen.ListHabitViewModel
import com.myapp.yourhabitsdoubletapp.databinding.FragmentBottomSheetBinding
import com.myapp.yourhabitsdoubletapp.di.ListHabitComponent
import com.myapp.yourhabitsdoubletapp.di.components
import javax.inject.Inject

class BottomSheetFragment() : Fragment(R.layout.fragment_bottom_sheet) {

    private var fragmentBottomSheetBinding: FragmentBottomSheetBinding? = null

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
        val binding = FragmentBottomSheetBinding.bind(view)
        fragmentBottomSheetBinding = binding
        initSpiner()
        filterList()
        binding.spinerSorted.setOnItemClickListener { _, _, _, _ ->
            listHabitViewModel.putSortType(getSortedType())
            listHabitViewModel.getSort()
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
                listHabitViewModel.getSort()
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
