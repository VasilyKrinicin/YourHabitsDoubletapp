package com.myapp.yourhabitsdoubletapp.ui.EditHabitFragment

import android.util.Log
import androidx.lifecycle.*
import com.myapp.data.NetworkRepositoryImp
import com.myapp.domain.model.HabitModel
import com.myapp.domain.usecase.*
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val CUSTOM_UID = "no_uid"

class EditHabitViewModel @Inject constructor(
    private val getHabitByUidUseCase: GetHabitByUidUseCase,
    private val addOrUpdateUseCase:AddOrUpdateUseCase,
    private val habitId: String
) : ViewModel(),
    CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private var mutableIdLiveData = MutableLiveData<String>()

    val getHabitLiveData: LiveData<HabitModel> =
        Transformations.switchMap(mutableIdLiveData) {
            getHabitByUidUseCase.execute(it)?.asLiveData()
        }

    init {
        if (habitId != CUSTOM_UID) {
            mutableIdLiveData.value = habitId
        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun fieldProcess(newHabit: HabitModel) {
        launch {
            addOrUpdateUseCase.execute(newHabit, CUSTOM_UID)
        }
    }
}
