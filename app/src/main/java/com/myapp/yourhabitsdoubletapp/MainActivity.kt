package com.myapp.yourhabitsdoubletapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.yourhabitsdoubletapp.Data.AdapterHabit
import com.myapp.yourhabitsdoubletapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private var adapterHabit: AdapterHabit? = null
    private var habit: ArrayList<Habit> = arrayListOf()
    private lateinit var binding: ActivityMainBinding
   private var startForResult: ActivityResultLauncher<Intent>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initList(habit)
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                when (result.resultCode) {
                    KEY_ADD -> {
                        val intent = result.data
                        val newHabit = intent?.getParcelableExtra<Habit>("new_habit")
                        adapterHabit?.addHabit(newHabit!!)
                        binding.habitList.scrollToPosition(adapterHabit?.itemCount?.minus(1) ?:0)
                        binding.textViewEmptyList.isVisible = habit.isEmpty()
                    }
                    KEY_EDIT -> {
                        val intent = result.data
                        val editHabit = intent?.getParcelableExtra<Habit>("habit")
                        adapterHabit?.editHabitList(editHabit!!)
                    }
                }
            }
        binding.addFab.setOnClickListener {
            //Вызываем второе активити для добавленеия новго элемента в список
            startForResult?.launch(Intent(this, DialogHabitsActivity::class.java))
        }

        binding.textViewEmptyList.isVisible = habit.isEmpty()
    }
    //Инициализируем адаптер списка Habit
    private fun initList(habit: ArrayList<Habit>) {
        adapterHabit = AdapterHabit(habit) { position -> modifyHabit(habit[position]) }
        with(binding.habitList) {
            this.adapter = adapterHabit
            this.layoutManager = LinearLayoutManager(context)
        }
        binding.textViewEmptyList.isVisible = habit.isEmpty()
    }
/* Ф-ия для определния интетнта и выбраного пользователем элемента списка Habit для редактирования
    и запуска активити редатирования*/
    private fun modifyHabit(habit: Habit) {
        val intent = DialogHabitsActivity.newIntent(this, habit)
        startForResult?.launch(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
outState.putParcelableArrayList(HABIT_STATE,habit)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        habit=savedInstanceState.getParcelableArrayList<Habit>(HABIT_STATE)?: arrayListOf()
        initList(habit)
    }
    companion object {
        private const val KEY_ADD = 1
        private const val KEY_EDIT = 2
        private const val HABIT_STATE = "HabitState"
    }
}
