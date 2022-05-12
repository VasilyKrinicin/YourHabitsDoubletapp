package com.myapp.yourhabitsdoubletapp.ui.ListHabitFragmen


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.myapp.yourhabitsdoubletapp.Data.SortType
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.database.Habit
import com.myapp.yourhabitsdoubletapp.database.HabitRepository

class ListHabitViewModel() : ViewModel() {

    private var habitSortType = MutableLiveData<SortType>()
    private var habitTextFilter = MutableLiveData<String>()
    private var habitTypeFilter = MutableLiveData<TypeHabit>()
    var habitMutableLiveData = MutableLiveData<List<Habit>>()

    val habitLiveData: LiveData<List<Habit>> = Transformations.switchMap(habitMutableLiveData) {
        HabitRepository.getSortFilterListHabit(
            getTypeHabit(),
            getSortType(),
            getTextFilter()
        )
    }

    fun getSort() {
        habitMutableLiveData.postValue(
            HabitRepository.getSortFilterListHabit(
                getTypeHabit(),
                getSortType(),
                getTextFilter()
            ).value
        )
    }

    private fun getTypeHabit(): TypeHabit? = habitTypeFilter.value

    private fun getSortType(): SortType? = habitSortType.value

    private fun getTextFilter(): String? = habitTextFilter.value

    fun putSortType(sortType: SortType) {
        habitSortType.value = sortType
    }

    fun setTextFilter(text: String) {
        habitTextFilter.value = text
    }

    fun setHabitTypeFilter(typeHabit: TypeHabit) {
        habitTypeFilter.value = typeHabit
    }

    fun getHabitSortType(): LiveData<SortType> = habitSortType
    fun getHabitTextFilter(): LiveData<String> = habitTextFilter
    fun getHabitTypeFilter(): LiveData<TypeHabit> = habitTypeFilter
}


