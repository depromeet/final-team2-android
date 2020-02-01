package com.def.team2.screen.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.def.team2.R

class IdolMapAdapter(
    private val itemClickCallback: (item: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.item_map_idol, parent, false),
            itemClickCallback
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(itemList[position])
    }

    fun setItems(data: List<String>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        itemView: View,
        clickCallback: (item: String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val idolImg: ImageView by lazy {
            itemView.findViewById<ImageView>(R.id.iv_map_idol)
        }
        private var imageUrl: String? = null

        init {
            itemView.setOnClickListener { _ ->
                imageUrl?.let {
                    clickCallback.invoke(it)
                }
            }
        }

        fun bind(imgUrl: String) {
            this.imageUrl = imgUrl.apply {
                Glide.with(itemView)
                    .load(this)
                    .into(idolImg)
            }
        }
    }
}