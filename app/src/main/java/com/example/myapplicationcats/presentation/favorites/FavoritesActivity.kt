package com.example.myapplicationcats.presentation.favorites

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationcats.R
import com.example.myapplicationcats.presentation.adapters.CustomAdapter
import com.example.myapplicationcats.di.AppModule
import com.example.myapplicationcats.di.DaggerAppComponent
import com.example.myapplicationcats.domain.models.CatModel
import kotlinx.android.synthetic.main.activity_favourites.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class FavoritesActivity : MvpAppCompatActivity(),
    FavoritesContractView {

    private val customAdapter: CustomAdapter by lazy { CustomAdapter(
        { cat -> presenter.onClickInFavorites(cat) },
        { cat -> presenter.onClickDownload(cat) }
    ) }

    @Inject
    lateinit var presenterLazy: dagger.Lazy<FavoritesPresenter>

    @InjectPresenter
    lateinit var presenter: FavoritesPresenter

    @ProvidePresenter
    fun providePresenter(): FavoritesPresenter {
        DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()
            .injectFavourites(this)

        return presenterLazy.get()
    }

    override fun installPermission() {
        requestPermissions(
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        recyclerViewFavourites.apply {
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            adapter = customAdapter
        }
    }

    override fun showCats(list: List<CatModel>) {
        customAdapter.updateItems(list)
    }

    override fun showDownloadErrorState() {
        Toast.makeText(applicationContext, "Ошибка загрузки изображения", Toast.LENGTH_SHORT).show()
    }

    override fun showFavoriteErrorState() {
        Toast.makeText(applicationContext, "Ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun showSuccessDownloadState() {
        Toast.makeText(applicationContext, "Фото загружено", Toast.LENGTH_SHORT).show()
    }

    override fun showUnknownErrorState() {
        Toast.makeText(applicationContext, "Неизвестная ошибка", Toast.LENGTH_SHORT).show()
    }

    override fun showInternetErrorState() {
        Toast.makeText(applicationContext, "Ошибка подключения", Toast.LENGTH_SHORT).show()
    }

    override fun showProgressBar(show: Boolean) {
        progress.isVisible = show
    }

    companion object{
        private const val STORAGE_PERMISSION_CODE = 1000
    }
}