package com.example.myapplicationcats.presentation.favorites

import com.example.myapplicationcats.domain.models.CatModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavoritesContractView : MvpView{

    fun showCats(list: List<CatModel>)

    fun showSuccessDownloadState()

    fun showDownloadErrorState()

    fun showFavoriteErrorState()

    fun showUnknownErrorState()

    fun showInternetErrorState()

    fun installPermission()

    fun showProgressBar(show: Boolean)
}