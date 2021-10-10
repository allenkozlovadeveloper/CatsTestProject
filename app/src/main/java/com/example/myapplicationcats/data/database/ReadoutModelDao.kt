package com.example.myapplicationcats.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplicationcats.data.entities.ModelCatFavourite
import io.reactivex.Single

@Dao
interface ReadoutModelDao {

    @Query("SELECT * FROM ModelCatFavourite")
    fun getCats(): Single<List<ModelCatFavourite>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCat(vararg todo: ModelCatFavourite)

    @Query("DELETE from ModelCatFavourite WHERE url = :urlStr")
    fun deleteCat(urlStr: String)

    @Query("DELETE FROM ModelCatFavourite")
    fun nukeTable()
}