package com.myapp.yourhabitsdoubletapp.Networking

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.myapp.yourhabitsdoubletapp.database.Habit
import com.myapp.yourhabitsdoubletapp.database.HabitRepository
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext


object NetworkRepository : CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job + CoroutineExceptionHandler { _, e -> throw e }


    private val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConfiguration.HOST_NAME)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttpClient)
        .build()

    val networkApi: NetworkApi
        get() = retrofit.create()

    fun synchronizeHabit() {
        loadToServer()
        loadToDb()
    }

    private fun loadToServer() {
        launch(Dispatchers.IO) {
            val listHabit = HabitRepository.getAll()
            listHabit.filter { !it.unloaded }
                .forEach { habit ->
                    habit.uid =
                        networkApi.putHabit(habit.toHabitNetwork()).execute().body()?.uid.orEmpty()
                    habit.unloaded = true
                    HabitRepository.editHabit(habit)
                }
        }
    }

    private fun loadToDb() {
        var listNetworkHabit = emptyList<HabitNetwork>()
        networkApi.getAllHabit().enqueue(
            object : Callback<List<HabitNetwork>> {

                override fun onResponse(
                    call: Call<List<HabitNetwork>>,
                    response: Response<List<HabitNetwork>>
                ) {
                    if (response.isSuccessful) {
                        listNetworkHabit = response.body() ?: emptyList()
                        listNetworkHabit.forEach { habitNetwork ->
                            launch {
                                val habit = HabitRepository.getHabitByUID(habitNetwork.uid)
                                if (habit == null) {
                                    val newHabit = habitNetwork.toHabit()
                                    newHabit.uid = habitNetwork.uid
                                    newHabit.unloaded = true
                                    HabitRepository.addHabit(newHabit)
                                }
                            }
                        }
                    } else {
                        Log.e("Response", "Error response getAllHabit")
                    }
                }

                override fun onFailure(call: Call<List<HabitNetwork>>, t: Throwable) {
                    Log.e("Response", "Error reqest $t")
                }

            }
        )

    }
}




