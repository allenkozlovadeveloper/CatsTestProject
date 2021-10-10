package com.example.myapplicationcats.domain.interactors

import com.example.myapplicationcats.data.repositories.DownloadRepository
import com.example.myapplicationcats.domain.models.CatModel
import com.example.myapplicationcats.data.entities.ModelCatFavourite
import com.example.myapplicationcats.data.repositories.FavouritesRepository
import com.example.myapplicationcats.data.repositories.MainRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class Interactor @Inject constructor(
    private val mainRepository: MainRepository,
    private val favouritesRepository: FavouritesRepository,
    private val downloadRepository: DownloadRepository
) {

    fun getCats(): Single<List<CatModel>> =
        Single.zip(
            mainRepository.searchCat(getRandomPage(), 20),
            favouritesRepository.selectCats(),
            { allCats, favCats ->
               allCats.map { cat ->
                   CatModel(
                       cat.url,
                       favCats.find { favCat ->
                           favCat.url == cat.url
                       }?.let { true } ?: false
                   )
               }

            })

    fun addCat(cat: ModelCatFavourite){
        favouritesRepository.addCat(cat)
    }

    fun delCat(cat: ModelCatFavourite) = favouritesRepository.delCat(cat)

    fun getFavouritesCats() : Single<List<CatModel>> =
            favouritesRepository.selectCats()
                .map { favoriteCats ->
                    favoriteCats.map { cat ->
                        CatModel(
                            cat.url,
                            true
                        )

                    }
                }

    fun downloadCat(catModel: CatModel): Single<Unit> = Single.just(downloadRepository.saveCat(catModel))

    fun checkPermission() = downloadRepository.checkPermission()

    fun clearStore() {
        mainRepository.clearStore()
    }

    private fun getRandomPage() = (1..100).random()
}