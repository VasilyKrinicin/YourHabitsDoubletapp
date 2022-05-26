package com.myapp.yourhabitsdoubletapp.ui.ListHabitFragmen


import androidx.lifecycle.*
import com.myapp.data.Networking.UID
import com.myapp.data.RepositoryNetwork
import com.myapp.data.Networking.HabitDone
import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.SortType
import com.myapp.domain.repository.RepositoryHabit
import com.myapp.domain.usecase.DeleteHabit
import com.myapp.domain.usecase.ExecutionHabit
import com.myapp.domain.usecase.GetHabitList
import com.myapp.domain.usecase.GetSortedFilterListHabit
import com.myapp.yourhabitsdoubletapp.di.ListHabitScoped
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.single
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ListHabitScoped
class ListHabitViewModel @Inject constructor(
    private val repositoryNetwork: RepositoryNetwork,
    private val repositoryHabit: RepositoryHabit,
    private val deleteHabit: DeleteHabit,
    private val executionHabit: ExecutionHabit,
    private val getSortedFilterListHabit: GetSortedFilterListHabit,
) : ViewModel(),
    CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private var habitSortType = MutableLiveData<SortType>()
    private var habitTextFilter = MutableLiveData<String>()
    var habitMutableLiveData = MutableLiveData<List<HabitModel>>()

    val habitLiveData: LiveData<List<HabitModel>> =
        Transformations.switchMap(habitMutableLiveData) {
            getSortedFilterListHabit.execute(
                getSortType(),
                getTextFilter()
            ).asLiveData()
        }

    init {
        repositoryNetwork.synchronizeHabit()
        getSort()

    }

    fun getSort() {
        habitMutableLiveData.postValue(
            getSortedFilterListHabit.execute(
                getSortType(),
                getTextFilter()
            ).asLiveData().value
        )
    }

    fun deleteHabit(habitModel: HabitModel) = launch {
        val uid = UID(habitModel.uid)
        deleteHabit.execute(habitModel)
        repositoryNetwork.deleteHabitNetwork(uid)
    }

    fun executeHabit(habit: HabitModel) = launch {
        val done = HabitDone(
            System.currentTimeMillis().toInt(),
            habit.uid
        )
        repositoryNetwork.postHabit(done)

    }

    private fun getSortType(): SortType? = habitSortType.value

    private fun getTextFilter(): String? = habitTextFilter.value

    fun putSortType(sortType: SortType) {
        habitSortType.postValue(sortType)
    }

    fun setTextFilter(text: String) {
        habitTextFilter.postValue(text)
    }

    fun getHabitSortType(): LiveData<SortType> = habitSortType
    fun getHabitTextFilter(): LiveData<String> = habitTextFilter

}


