package com.example.myapplicationcats.domain.models

data class CatModel(
    val url: String,
    val favorite: Boolean = false,
    val downloaded: Boolean = false
)