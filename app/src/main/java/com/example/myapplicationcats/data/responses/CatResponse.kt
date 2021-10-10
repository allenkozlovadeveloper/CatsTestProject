package com.example.myapplicationcats.data.responses

import com.google.gson.annotations.SerializedName

data class CatResponse (
    @SerializedName("url") val url: String?
)