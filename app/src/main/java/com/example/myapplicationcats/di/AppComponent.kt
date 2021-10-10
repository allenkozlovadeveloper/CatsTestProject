package com.example.myapplicationcats.di

import com.example.myapplicationcats.presentation.favorites.FavoritesActivity
import com.example.myapplicationcats.presentation.main.MainActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun injectFavourites(activity: FavoritesActivity)
}