package com.myapp.data.Networking

import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface NetworkApi {
    @GET("/api/habit")
    suspend fun getAllHabit(): List<HabitNetwork>

    @PUT("/api/habit")
    suspend fun putHabit(@Body habitNetwork: HabitNetwork): Flow<UID>

   @HTTP(method = "DELETE", path ="/api/habit", hasBody = true)
    //@DELETE("/api/habit" )
    suspend fun deleteHabit(@Body uid:UID)

  //  @POST("/api/habit_done")
    @HTTP(method = "POST", path ="/api/habit_done", hasBody = true)
    suspend fun postHabit(@Body done: HabitDone)
}