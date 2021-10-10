package com.example.myapplicationcats.data.repositories

import com.example.myapplicationcats.domain.models.CatModel
import com.example.myapplicationcats.data.api.ApiService
import com.example.myapplicationcats.data.mappers.CatMapper
import com.example.myapplicationcats.data.store.CatsDataStore
import io.reactivex.Single
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val catMapper: CatMapper,
    private val apiService: ApiService,
    private val catsDataStore: CatsDataStore
) {

    fun searchCat(page: Int,limit: Int): Single<List<CatModel>> =
        catsDataStore.getCats()
            .switchIfEmpty(
                apiService.getListCats(page, limit)
                    .map { responseList ->
                        responseList.map { response ->
                            catMapper(response)
                        }
                    }
                    .doOnSuccess { cats ->
                        catsDataStore.setCats(cats)
                    }
            )

    fun clearStore() {
        catsDataStore.clearCats()
    }
}