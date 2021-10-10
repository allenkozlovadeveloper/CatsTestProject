package com.example.myapplicationcats.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ModelCatFavourite (
    @PrimaryKey(autoGenerate = true) var  id: Int? = null,
    @ColumnInfo val url: String
)