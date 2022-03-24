package com.myapp.yourhabitsdoubletapp

import android.opengl.Visibility
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.GONE

import com.google.android.material.tabs.TabLayoutMediator
import com.myapp.yourhabitsdoubletapp.Adapters.TabPagerAdapter

import com.myapp.yourhabitsdoubletapp.UI.DialogHabitFragment
import com.myapp.yourhabitsdoubletapp.databinding.ActivityMainBinding

import java.util.ArrayList

class MainActivity : AppCompatActivity(), EditItemInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private var habitList: ArrayList<Habit> = arrayListOf()
    private var adapterTabPager: TabPagerAdapter? = null
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelableArrayList(NEW_HABIT, habitList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.open_menu,
            R.string.close_menu
        )
        binding.drawerLayout.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.menu_item_about->{
                    Toast.makeText(this,"Кнопка меню о прилжении",Toast.LENGTH_LONG).show()
                }
                R.id.menu_item_home->{
                Toast.makeText(this,"Кнопка меню домой",Toast.LENGTH_LONG).show()
                    }

            }
            true
        }



        if (savedInstanceState != null) {
            val habit = savedInstanceState.getParcelableArrayList<Habit>(NEW_HABIT) ?: arrayListOf()
            habitList = habit
        }

        initViewPager(habitList)
        binding.addFab.setOnClickListener {
            val fragment = DialogHabitFragment()
            fragment.show(supportFragmentManager, null)
        }
    }

    private fun initViewPager(habit: ArrayList<Habit>) {
        adapterTabPager = TabPagerAdapter(habit, this)
        binding.apply {
            with(viewPager) {
                adapter = adapterTabPager
            }
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = "tab$position"
            }.attach()
        }
    }

    companion object {
        private const val NEW_HABIT = "new_habit"
        private const val EDIT_HABIT = "edit_habit"
        private const val KEY_ADD = 1
        private const val KEY_EDIT = 2
    }

    override fun editItem(habit: Habit) {
        adapterTabPager?.updateItem(habit)
        editHabitList(habit)
        initViewPager(habitList)
    }

    private fun editHabitList(habit: Habit) {
        habitList.forEachIndexed { index, it ->
            if (it.id == habit.id) {
                habitList[index] = habit
            }
        }
    }

    override fun newItem(habit: Habit) {
        adapterTabPager?.addItem(habit)
        habitList.add(habit)
        initViewPager(habitList)
    }
}
