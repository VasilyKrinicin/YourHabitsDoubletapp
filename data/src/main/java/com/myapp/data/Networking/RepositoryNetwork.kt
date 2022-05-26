package com.myapp.data

import android.util.Log
import com.myapp.data.Networking.NetworkApi
import com.myapp.data.Networking.UID
import com.myapp.data.convert.ConvertModel
import com.myapp.data.Networking.HabitDone
import com.myapp.domain.model.HabitModel
import com.myapp.domain.repository.RepositoryHabit
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class RepositoryNetwork(
    private val repositoryHabit: RepositoryHabit,
    private val networkApi: NetworkApi
) : CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    fun synchronizeHabit() {
        loadToServer()
        loadToDb()
    }

    suspend fun putHabit(newHabit: HabitModel): String = coroutineScope {
        withContext(Dispatchers.IO) {
            networkApi.putHabit(
                ConvertModel.convertHabitModelToHabit(
                    newHabit
                ).toHabitNetwork()
            ).single().uid
        }
    }

    suspend fun deleteHabitNetwork(uid: UID) = coroutineScope {
        withContext(Dispatchers.IO) {
            try {
                networkApi.deleteHabit(uid)
            } catch (e: Exception) {
                Log.e("Error TryCatch", "Error Delete", e)
            }
        }
    }

    suspend fun postHabit(done: HabitDone) {
        withContext(Dispatchers.IO) {
            try {
                networkApi.postHabit(done)
            } catch (e: Exception) {
                Log.e("Error TryCatch", "Error POST", e)
            }
        }

    }

    fun loadToServer() {
        launch(Dispatchers.IO) {
            val listHabit = repositoryHabit.getAll()
            listHabit.map {
                it.filter { it.unloaded }
                    .forEach { habit ->
                        try {
                            habit.uid = networkApi.putHabit(
                                ConvertModel.convertHabitModelToHabit(habit).toHabitNetwork()
                            ).single().uid
                            habit.unloaded = true
                            repositoryHabit.editHabit(habit)
                        } catch (e: Exception) {
                            Log.e("Error TryCatch", "Error loadToServer", e)

                        }
                    }
            }
        }
    }

    private fun loadToDb() {
        launch(Dispatchers.IO) {
            try {
                val listNetworkHabit = networkApi.getAllHabit()
                listNetworkHabit.forEach { habitNetwork ->
                    val newHabit = habitNetwork.toHabit()
                    newHabit.uid = habitNetwork.uid
                    newHabit.unloaded = true
                    repositoryHabit.addHabit(ConvertModel.convertHabitToHabitModel(newHabit))
                }

            } catch (e: Exception) {
                Log.e("Error TryCatch", "Error loadToDb", e)

            }
        }
    }
}


