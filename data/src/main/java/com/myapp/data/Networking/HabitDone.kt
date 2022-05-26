package com.myapp.data.Networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class HabitDone(
    val date:Int,
   @Json(name = "habit_uid") val habit_uid:String) {
}