package com.myapp.data.Networking

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UID(@Json(name = "uid") var uid: String)