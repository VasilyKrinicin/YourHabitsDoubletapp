package com.myapp.data

import android.util.Log
import com.myapp.data.Networking.NetworkApi
import com.myapp.data.convert.ConvertModel
import com.myapp.domain.model.HabitDoneModel
import com.myapp.domain.model.HabitModel
import com.myapp.domain.model.UidModel
import com.myapp.domain.repository.NetworkRepository
import com.myapp.domain.repository.HabitRepository
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class NetworkRepositoryImp(
    private val habitRepository: HabitRepository,
    private val networkApi: NetworkApi
) : CoroutineScope, NetworkRepository {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun synchronizeHabit() {
     //   loadToServer()
     //   loadToDb()
    }

    override suspend fun putHabit(newHabit: HabitModel): String =
        networkApi.putHabit(
            ConvertModel.convertHabitModelToHabit(
                newHabit
            ).toHabitNetwork()
        ).execute().body()!!.uid


    override suspend fun deleteHabitNetwork(uidModel: UidModel): Unit = coroutineScope {
        withContext(Dispatchers.IO) {
            try {
                networkApi.deleteHabit(uidModel)
            } catch (e: Exception) {
                Log.e("Error TryCatch", "Error Delete", e)
            }
        }
    }

    override suspend fun postHabit(doneModel: HabitDoneModel) {
        withContext(Dispatchers.IO) {
            try {
                networkApi.postHabit(doneModel)
            } catch (e: Exception) {
                Log.e("Error TryCatch", "Error POST", e)
            }
        }

    }

    override fun loadToServer() {
        launch(Dispatchers.IO) {
            val listHabitFlow = habitRepository.getAll()
            listHabitFlow.collect { listHabit ->
                listHabit.forEach { habit ->
                    val habitOld = habit
                    if (!habit.unloaded) {
                        try {
                            habit.uid = ""
                            habit.uid = networkApi.putHabit(
                                ConvertModel.convertHabitModelToHabit(habit).toHabitNetwork()
                            ).execute().body()!!.uid
                        } catch (e: Exception) {
                            habit.uid = habitOld.uid
                            habit.unloaded = false
                            Log.e("Error TryCatch", "Error loadToServer", e)

                        } finally {
                            habitRepository.deleteHabit(habitOld)
                            habitRepository.editHabit(habit)
                        }
                    }
                }
            }
        }
    }

    override fun loadToDb() {
        launch(Dispatchers.IO) {
            try {
                val listNetworkHabit = networkApi.getAllHabit()
                listNetworkHabit.forEach { habitNetwork ->
                    val newHabit = habitNetwork.toHabit()
                    newHabit.unloaded = true
                    habitRepository.addHabit(ConvertModel.convertHabitToHabitModel(newHabit))
                }
            } catch (e: Exception) {
                Log.e("Error TryCatch", "Error loadToDb", e)
            }
        }
    }
}


