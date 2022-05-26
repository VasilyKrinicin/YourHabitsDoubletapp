package com.myapp.yourhabitsdoubletapp.ui.EditHabitFragment

import android.util.Log
import androidx.lifecycle.*
import com.myapp.data.RepositoryNetwork
import com.myapp.domain.model.HabitModel
import com.myapp.domain.usecase.*
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val CUSTOM_UID = "no_uid"

class EditHabitViewModel @Inject constructor(
    private val addHabit: AddHabit,
    private val updateHabit: UpdateHabit,
    private val getHabitByUid: GetHabitByUid,
    private val repositoryNetwork: RepositoryNetwork,
    private val habitId: String
) : ViewModel(),
    CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private var mutableIdLiveData = MutableLiveData<String>()

    val getHabitLiveData: LiveData<HabitModel> =
        Transformations.switchMap(mutableIdLiveData) {
            getHabitByUid.execute(it).asLiveData()
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
            if (newHabit.uid == CUSTOM_UID) {
                try {
                    newHabit.uid = repositoryNetwork.putHabit(newHabit)
                    newHabit.unloaded = true

                } catch (e: Exception) {
                    Log.e("Error TryCatch", "Error add habit", e)
                    newHabit.uid = CUSTOM_UID
                    newHabit.unloaded = false
                } finally {
                    addHabit.execute(newHabit)
                }
            } else {
                try {
                    newHabit.uid = repositoryNetwork.putHabit(newHabit)
                    newHabit.unloaded = true
                } catch (e: Exception) {
                    Log.e("Error TryCatch", "Error update habit", e)
                    newHabit.uid = CUSTOM_UID
                    newHabit.unloaded = false
                } finally {
                    updateHabit.execute(newHabit)
                }
            }
        }
    }
}
