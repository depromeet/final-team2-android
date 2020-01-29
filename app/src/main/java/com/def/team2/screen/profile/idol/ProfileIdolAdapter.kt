package com.def.team2.screen.profile.idol

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R
import com.def.team2.base.BaseViewHolder
import com.def.team2.util.inflate

class ProfileIdolAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val items = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =holder.bindData(items[position])

    inner class ViewHolder private constructor(itemView: View) : BaseViewHolder(itemView) {
        constructor(parent: ViewGroup) : this(parent.inflate(R.layout.item_profile_idol, false))

        override fun onDataBind(data: Any?) {

        }

    }
}