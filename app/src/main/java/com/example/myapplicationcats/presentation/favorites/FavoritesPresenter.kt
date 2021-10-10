package com.example.myapplicationcats.presentation.favorites

import com.example.myapplicationcats.domain.models.CatModel
import com.example.myapplicationcats.data.entities.ModelCatFavourite
import com.example.myapplicationcats.domain.interactors.Interactor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import java.net.UnknownHostException
import javax.inject.Inject

@InjectViewState
class FavoritesPresenter @Inject constructor(
    private val interactor: Interactor
) : MvpPresenter<FavoritesContractView>() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: FavoritesContractView) {
        super.attachView(view)
        viewState.showProgressBar(true)
        disposables.add(
            interactor.getFavouritesCats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showProgressBar(false)
                    viewState.showCats(it)
                }, { throwable ->
                    viewState.showProgressBar(false)
                    if (throwable is UnknownHostException) viewState.showInternetErrorState()
                    else viewState.showUnknownErrorState()
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun onClickInFavorites(catModel: CatModel) {
        disposables.add(
            Single.just(catModel)
                .map { ModelCatFavourite(url = it.url) }
                .doOnSuccess {
                    interactor.delCat(it)
                }
                .flatMap { interactor.getFavouritesCats() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showCats(it)
                }, {
                    viewState.showFavoriteErrorState()
                })
        )
    }

    fun onClickDownload(catModel: CatModel) {
        if (!interactor.checkPermission())
            viewState.installPermission()

        disposables.add(
            interactor.downloadCat(catModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showSuccessDownloadState()
                }, {
                    viewState.showDownloadErrorState()
                })
        )
    }
}