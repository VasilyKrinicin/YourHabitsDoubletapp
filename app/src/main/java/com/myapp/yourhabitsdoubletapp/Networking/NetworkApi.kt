package com.myapp.yourhabitsdoubletapp.Networking

import retrofit2.Call
import retrofit2.http.*

interface NetworkApi {
    @GET("/api/habit")
    fun getAllHabit(): Call<List<HabitNetwork>>

    @PUT("/api/habit")
    fun putHabit(@Body habitNetwork: HabitNetwork): Call<UID>

    @DELETE("/api/habit")
    suspend fun deleteHabit(@Body habitNetwork: HabitNetwork)

    @POST("/api/habit_done")
    suspend fun postHabit(date: Int, uid: String)
}