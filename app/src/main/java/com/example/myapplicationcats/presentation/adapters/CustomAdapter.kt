package com.example.myapplicationcats.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationcats.R
import com.example.myapplicationcats.presentation.holders.CatsViewHolder
import com.example.myapplicationcats.domain.models.CatModel

class CustomAdapter(
    private val favoriteListener: (CatModel) -> Unit,
    private val downloadListener: (CatModel) -> Unit
) : RecyclerView.Adapter<CatsViewHolder>() {

    private val items: MutableList<CatModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return CatsViewHolder(view, favoriteListener, downloadListener)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(list: List<CatModel>){
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}