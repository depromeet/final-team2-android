package com.def.team2.screen.signup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R
import com.def.team2.network.model.Idol
import com.def.team2.network.model.IdolDto
import com.def.team2.network.model.School

class SearchAdapter<T>(
    private val itemClickCallback: (item: T) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.item_search, parent, false),
            itemClickCallback
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder<T>).bind(itemList[position])
    }

    fun setItems(data: List<T>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder<T>(
        itemView: View,
        clickCallback: (item: T) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_signup_item_title)
        }

        private var item: T? = null

        init {

            itemView.setOnClickListener { _ ->
                item?.let {
                    clickCallback.invoke(it)
                }
            }
        }

        fun bind(item: T) {
            this.item = item
            when (item) {
                is School -> textView.text = item.name
                is IdolDto -> textView.text = item.name

            }
        }
    }
}