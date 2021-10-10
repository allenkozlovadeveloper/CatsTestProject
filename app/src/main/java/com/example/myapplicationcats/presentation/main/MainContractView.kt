package com.example.myapplicationcats.presentation.main

import com.example.myapplicationcats.domain.models.CatModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainContractView : MvpView {

    fun showCats(list: List<CatModel>)

    fun installPermission()

    fun showSuccessAddToFavoritesState()

    fun transitionToFavouritesCats()

    fun showSuccessDownloadState()

    fun showDownloadErrorState()

    fun showFavoriteErrorState()

    fun showUnknownErrorState()

    fun showInternetErrorState()

    fun showProgressBar(show: Boolean)
}