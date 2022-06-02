package com.myapp.yourhabitsdoubletapp.ui.ListHabitFragmen


import androidx.lifecycle.*
import com.myapp.data.NetworkRepositoryImp
import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.SortType
import com.myapp.domain.model.UidModel
import com.myapp.domain.repository.HabitRepository
import com.myapp.domain.usecase.DeleteHabitUseCase
import com.myapp.domain.usecase.ExecutionHabitUseCase
import com.myapp.domain.usecase.GetSortedFilterListHabitUseCase
import com.myapp.yourhabitsdoubletapp.di.ListHabitScoped
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@ListHabitScoped
class ListHabitViewModel @Inject constructor(
    private val networkRepositoryImp: NetworkRepositoryImp,
    private val habitRepository: HabitRepository,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val executionHabitUseCase: ExecutionHabitUseCase,
    private val getSortedFilterListHabitUseCase: GetSortedFilterListHabitUseCase,
) : ViewModel(),
    CoroutineScope {
    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
    private var textMassageMutableLiveData = MutableLiveData<String>()
    private var habitSortType = MutableLiveData<SortType>()
    private var habitTextFilter = MutableLiveData<String>()
    var habitMutableLiveData = MutableLiveData<List<HabitModel>>()

    val textMassageLiveData: LiveData<String>
        get() = textMassageMutableLiveData
    val habitLiveData: LiveData<List<HabitModel>> =
        Transformations.switchMap(habitMutableLiveData) {
            getSortedFilterListHabitUseCase.execute(
                getSortType(),
                getTextFilter()
            ).asLiveData()
        }

    init {
        networkRepositoryImp.synchronizeHabit()
        getSort()

    }

    fun getSort() {
        habitMutableLiveData.postValue(
            getSortedFilterListHabitUseCase.execute(
                getSortType(),
                getTextFilter()
            ).asLiveData().value
        )
    }

    fun deleteHabit(habitModel: HabitModel) = launch {
        val uid = UidModel(habitModel.uid)
        deleteHabitUseCase.execute(habitModel)
        networkRepositoryImp.deleteHabitNetwork(uid)
    }

    suspend fun executeHabit(habit: HabitModel) {
        viewModelScope.launch {
            textMassageMutableLiveData.postValue(executionHabitUseCase.execute(habit))
        }
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


