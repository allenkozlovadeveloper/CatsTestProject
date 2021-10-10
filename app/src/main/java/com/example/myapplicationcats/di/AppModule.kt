package com.example.myapplicationcats.di

import android.app.Application
import com.example.myapplicationcats.data.api.ApiService
import com.example.myapplicationcats.data.mappers.CatMapper
import com.example.myapplicationcats.data.store.CatsDataStore
import com.example.myapplicationcats.data.database.ReadoutModelDao
import com.example.myapplicationcats.data.repositories.DownloadRepository
import com.example.myapplicationcats.data.repositories.MainRepository
import com.example.myapplicationcats.data.database.dbAbstract
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideMainRepository(): MainRepository {
        return MainRepository(catMapper, apiService, catsDataStore)
    }
    @get:Provides
    val apiService = ApiService.create()

    @get:Provides
    val catMapper: CatMapper = CatMapper()

    @get:Provides
    val catsDataStore: CatsDataStore = CatsDataStore()

    @Provides
    fun providesDbAbstract(): ReadoutModelDao {
        return dbAbstract.getDatabase(application).catsDao()
    }

    @Provides
    fun provideDownloadRepository(): DownloadRepository {
        return DownloadRepository(application.applicationContext)
    }
}