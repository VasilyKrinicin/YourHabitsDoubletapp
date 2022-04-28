package com.myapp.yourhabitsdoubletapp.Adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.ui.ListHabitFragmen.ListHabitFragment


class TabPagerAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {


    private val typeHabit = listOf<TypeHabit>(
        TypeHabit.POSITIVE,
        TypeHabit.NEGATIVE
    )

    override fun getItemCount(): Int {
        return typeHabit.size
    }

    override fun createFragment(position: Int): Fragment {
        val typeHabit = TypeHabit.values()[position]
        return ListHabitFragment.newInstance(typeHabit)
    }
}