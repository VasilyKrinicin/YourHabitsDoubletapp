package com.myapp.yourhabitsdoubletapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.myapp.yourhabitsdoubletapp.Adapters.TabPagerAdapter
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.R
import com.myapp.yourhabitsdoubletapp.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {
    private var fragmentViewPagerBinding: FragmentViewPagerBinding? = null
    private var adapterTabPager: TabPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentViewPagerBinding.bind(view)
        fragmentViewPagerBinding = binding
        initViewPager()
        binding.addFab.setOnClickListener {
            findNavController().navigate(R.id.editHabitFragment)
        }
    }

    private fun initViewPager() {
        adapterTabPager = TabPagerAdapter(requireActivity())
        fragmentViewPagerBinding?.apply {
            with(viewPager) {
                isSaveEnabled = false
                adapter = adapterTabPager
            }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = TypeHabit.values()[position].str
            }.attach()
        }
    }
}
