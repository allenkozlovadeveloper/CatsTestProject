package com.example.myapplicationcats.presentation.holders

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplicationcats.domain.models.CatModel
import kotlinx.android.synthetic.main.recycler_item.view.*

class CatsViewHolder(
    private val view: View,
    private val favoriteListenerInFavourites: (CatModel) -> Unit,
    private val favoriteListenerDownload: (CatModel) -> Unit
) : RecyclerView.ViewHolder(view) {

    private val imageView = view.image
    private val buttonDownload = view.btnDownload
    private val buttonFavourites = view.floatingActionButtonInFavourites

    fun bindItems(catModel: CatModel) {

        buttonDownload.setOnClickListener { favoriteListenerDownload(catModel) }
        buttonFavourites.setOnClickListener { favoriteListenerInFavourites(catModel) }

        buttonFavourites.setColorFilter(
            if (catModel.favorite)Color.parseColor("#A80A0A")
            else  Color.parseColor("#FFFFFF")
        )

        Glide
            .with(view.context)
            .load(catModel.url)
            .fitCenter()
            .into(imageView)

    }
}