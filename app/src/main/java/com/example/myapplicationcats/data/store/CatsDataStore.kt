package com.example.myapplicationcats.data.store

import com.example.myapplicationcats.domain.models.CatModel
import io.reactivex.Maybe

class CatsDataStore {
    private var cats: List<CatModel>? = null

    fun getCats(): Maybe<List<CatModel>> =
        cats?.let { cats -> Maybe.just(cats) } ?: Maybe.empty()

    fun setCats(cats: List<CatModel>) {
        this.cats = cats
    }

    fun clearCats() {
        cats = null
    }
}