package com.example.myapplicationcats.data.repositories

import com.example.myapplicationcats.data.database.ReadoutModelDao
import com.example.myapplicationcats.data.entities.ModelCatFavourite
import io.reactivex.Single
import javax.inject.Inject

class FavouritesRepository @Inject constructor(
    private val dbCatsFavourites: ReadoutModelDao
){

    fun selectCats(): Single<List<ModelCatFavourite>> = dbCatsFavourites.getCats()

    fun addCat(cat: ModelCatFavourite){
        with(dbCatsFavourites) {
            this.insertCat(cat)
        }
    }

    fun delCat(cat: ModelCatFavourite) {
        with(dbCatsFavourites) {
            this.deleteCat(cat.url)
        }
    }
}