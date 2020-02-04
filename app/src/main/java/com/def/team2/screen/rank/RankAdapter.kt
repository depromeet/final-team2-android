package com.def.team2.screen.rank

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R
import com.def.team2.base.BaseViewHolder
import com.def.team2.util.inflate

class RankAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    interface Callback {
        fun requestVote()
        fun entranceCommunity()
    }

    enum class ViewType {
        FIRST, RANK
    }

    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ViewType.FIRST.ordinal -> FirstViewHolder(parent)
        ViewType.RANK.ordinal -> ViewHolder(parent)
        else -> throw IllegalArgumentException("Unknown viewType=$viewType")
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        holder.bindData(items[position])

    override fun getItemViewType(position: Int) = items[position].viewType.ordinal

    fun setItems(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun updateItem(item: Item) {
        val currentItem = items.find { current -> current.data == item.data }
        if (currentItem != null) {
            val currentIndex = items.indexOf(currentItem)
            items[currentIndex] = item
            notifyItemChanged(currentIndex)
        }
    }

    inner class FirstViewHolder private constructor(itemView: View) : BaseViewHolder(itemView) {
        constructor(parent: ViewGroup) : this(parent.inflate(R.layout.item_rank_first, false))

        override fun onDataBind(data: Any?) {

        }

    }

    inner class ViewHolder private constructor(itemView: View) : BaseViewHolder(itemView) {
        constructor(parent: ViewGroup) : this(parent.inflate(R.layout.item_rank, false))

        override fun onDataBind(data: Any?) {

        }

    }

    data class Item(
        val data: Any,
        val viewType: ViewType
    )

}