package com.def.team2.screen.profile.idol

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R
import com.def.team2.base.BaseViewHolder
import com.def.team2.network.model.IdolGroup
import com.def.team2.util.imageLoad
import com.def.team2.util.inflate
import kotlinx.android.synthetic.main.item_profile_idol.*

class ProfileIdolAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val items = mutableListOf<IdolGroup>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent)

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =holder.bindData(items[position])

    fun setDatae(datas:List<IdolGroup>){
        items.clear()
        items.addAll(datas)
        notifyDataSetChanged()
    }

    inner class ViewHolder private constructor(itemView: View) : BaseViewHolder(itemView) {
        constructor(parent: ViewGroup) : this(parent.inflate(R.layout.item_profile_idol, false))

        override fun onDataBind(data: Any?) {
            if(data !is IdolGroup) return
            item_profile_idol_delete.isVisible = false
            data.circleImage?.apply {
                item_profile_idol_img.imageLoad(this)
            } ?: item_profile_idol_img.imageLoad(R.drawable.marker_default)
            item_profile_idol_name.text = data.name
        }

    }
}