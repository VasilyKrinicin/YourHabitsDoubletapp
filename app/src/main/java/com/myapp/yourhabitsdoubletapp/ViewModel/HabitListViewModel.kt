package com.myapp.yourhabitsdoubletapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.yourhabitsdoubletapp.Data.SortType
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.Habit

class HabitListViewModel : ViewModel() {
    private val habitStockLiveData = MutableLiveData<List<Habit>>()
    private val habitPositiveLiveData = MutableLiveData<List<Habit>>()
    private val habitNegativeLiveData = MutableLiveData<List<Habit>>()
    private val listHabitRepository = HabitRepository

    init {
        habitStockLiveData.value = listHabitRepository.getHabitList()
    }

    fun divisionIntoTypes(typeHabit: TypeHabit): LiveData<List<Habit>> {
        return when (typeHabit) {
            TypeHabit.NEGATIVE -> {
                habitNegativeLiveData.postValue(
                    habitStockLiveData.value
                        ?.filter { it.typeHabit == TypeHabit.NEGATIVE })
                habitNegativeLiveData
            }
            TypeHabit.POSITIVE -> {
                habitPositiveLiveData.postValue(
                    habitStockLiveData.value
                        ?.filter { it.typeHabit == TypeHabit.POSITIVE })
                habitPositiveLiveData
            }
        }
    }

    fun sortedAndFiltered(habitList: List<Habit>, sortType: SortType, text: String?) {
        habitStockLiveData.value = listHabitRepository.filterAndSort(habitList, sortType, text)
        updateSortFilter(habitStockLiveData.value!!)
    }

    private fun updateSortFilter(habitList: List<Habit>) {
        val mutableListHabitPositive = mutableListOf<Habit>()
        val mutableListHabitNegative = mutableListOf<Habit>()
        habitList.forEach {
            if (it.typeHabit == TypeHabit.NEGATIVE) {
                mutableListHabitNegative.add(it)
            } else {
                mutableListHabitPositive.add(it)
            }
        }
        habitNegativeLiveData.postValue(mutableListHabitNegative)
        habitPositiveLiveData.postValue(mutableListHabitPositive)
    }
}