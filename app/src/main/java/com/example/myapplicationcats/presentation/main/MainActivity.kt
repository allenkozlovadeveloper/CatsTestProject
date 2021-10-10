package com.example.myapplicationcats.presentation.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationcats.R
import com.example.myapplicationcats.presentation.adapters.CustomAdapter
import com.example.myapplicationcats.di.AppModule
import com.example.myapplicationcats.di.DaggerAppComponent
import com.example.myapplicationcats.domain.models.CatModel
import com.example.myapplicationcats.presentation.favorites.FavoritesActivity
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainContractView {

    @Inject
    lateinit var presenterLazy: dagger.Lazy<MainPresenter>

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()
            .inject(this)

        return presenterLazy.get()
    }

    private val customAdapter: CustomAdapter by lazy { CustomAdapter(
        { cat -> presenter.onClickInFavorites(cat) },
        { cat -> presenter.onClickDownload(cat) }
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = customAdapter
        }

        buttonFavourites.setOnClickListener { presenter.onFavoritesClick() }
    }

    override fun showCats(list: List<CatModel>) {
        customAdapter.updateItems(list)
    }

    override fun installPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    override fun showSuccessAddToFavoritesState() {
        Toast.makeText(applicationContext, "Картинка добавлена", Toast.LENGTH_SHORT).show()
    }

    override fun transitionToFavouritesCats() {
        val intent = Intent(this, FavoritesActivity::class.java)
        startActivity(intent)
    }

    override fun showSuccessDownloadState() {
        Toast.makeText(applicationContext, "Фото загружено", Toast.LENGTH_SHORT).show()
    }

    override fun showFavoriteErrorState() {
        Toast.makeText(applicationContext, "Ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun showUnknownErrorState() {
        Toast.makeText(applicationContext, "Неизвестная ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun showInternetErrorState() {
        Toast.makeText(applicationContext, "Ошибка подключения", Toast.LENGTH_SHORT).show()
    }

    override fun showDownloadErrorState() {
        Toast.makeText(applicationContext, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar(show: Boolean) {
        progress.isVisible = show
    }

    companion object {
           private const val STORAGE_PERMISSION_CODE = 1000
    }
}