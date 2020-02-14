package com.def.team2.screen.search

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R
import com.def.team2.base.BaseViewHolder
import com.def.team2.network.model.Idol
import com.def.team2.network.model.School
import com.def.team2.util.inflate
import kotlinx.android.synthetic.main.item_search.*

class SearchAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val items = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bindData(items[position])

    fun setData(data: List<Any>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder private constructor(itemView: View) : BaseViewHolder(itemView) {
        constructor(parent: ViewGroup) : this(parent.inflate(R.layout.item_search, false))

        override fun onDataBind(data: Any?) {
            when (data) {
                is School -> tv_signup_item_title.text = data.name
                is Idol -> tv_signup_item_title.text = data.name
            }
        }

    }

}