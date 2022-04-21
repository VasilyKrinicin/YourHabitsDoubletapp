package com.myapp.yourhabitsdoubletapp.ViewModel


import androidx.lifecycle.*
import com.myapp.yourhabitsdoubletapp.Data.SortType
import com.myapp.yourhabitsdoubletapp.Data.TypeHabit
import com.myapp.yourhabitsdoubletapp.database.Habit
import com.myapp.yourhabitsdoubletapp.database.HabitRepository

class HabitListViewModel : ViewModel() {

    private var habitSortType = MutableLiveData<SortType>()
    private var habitTextFilter = MutableLiveData<String>()
    private var habitTypeFilter = MutableLiveData<TypeHabit>()

    private var habitMutableLiveData = MutableLiveData<List<Habit>>()

    var habitLiveData: LiveData<List<Habit>> = Transformations.switchMap(habitMutableLiveData) {
        HabitRepository.getSortFilterListHabit(getTypeHabit(), getSortType(), getTextFilter())
    }

    fun getHabitById(id: Long) = HabitRepository.getHabitById(id)

    fun getSort() {
        habitMutableLiveData.postValue(
            HabitRepository.getSortFilterListHabit(
                getTypeHabit(),
                getSortType(),
                getTextFilter()
            ).value
        )

    }

    private fun getTypeHabit(): TypeHabit? {
        return habitTypeFilter.value
    }


    private fun getSortType(): SortType? {
        return habitSortType.value
    }

    fun putSortType(sortType: SortType) {
        habitSortType.value = sortType
    }

    private fun getTextFilter(): String? {
        return habitTextFilter.value
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

