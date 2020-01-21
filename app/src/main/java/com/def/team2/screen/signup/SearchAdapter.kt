package com.def.team2.screen.signup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R

class SearchAdapter(
    private val itemClickCallback: (item: Pair<Int, String>) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = mutableListOf<Pair<Int, String>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.item_signup_search, parent, false),
            itemClickCallback
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(itemList[position])
    }

    fun setItems(data: List<Pair<Int, String>>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        itemView: View,
        clickCallback: (item: Pair<Int, String>) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val textView: TextView by lazy {
            itemView.findViewById<TextView>(R.id.tv_signup_item_title)
        }

        private var id: Int? = null

        init {

            itemView.setOnClickListener {
                id?.let {itemId ->
                    clickCallback.invoke(Pair(itemId, textView.text.toString()))
                }
            }
        }

        fun bind(idAndText: Pair<Int, String>) {
            id = idAndText.first
            textView.text = idAndText.second
        }
    }
}