package com.myapp.data.Networking

import com.myapp.domain.model.HabitDoneModel
import com.myapp.domain.model.UidModel
import retrofit2.Call
import retrofit2.http.*

interface NetworkApi {
    @GET("/api/habit")
    suspend fun getAllHabit(): List<HabitNetwork>

    @PUT("/api/habit")
    fun putHabit(@Body habitNetwork: HabitNetwork): Call<UID>

    @HTTP(method = "DELETE", path ="/api/habit", hasBody = true)
    //@DELETE("/api/habit" )
    suspend fun deleteHabit(@Body uidModel: UidModel)

  //  @POST("/api/habit_done")
    @HTTP(method = "POST", path ="/api/habit_done", hasBody = true)
    suspend fun postHabit(@Body doneModel: HabitDoneModel)
}