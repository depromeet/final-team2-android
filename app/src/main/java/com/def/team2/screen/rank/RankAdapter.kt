package com.def.team2.screen.rank

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R
import com.def.team2.base.BaseViewHolder
import com.def.team2.network.model.IdolGroup
import com.def.team2.network.model.IdolGroupResponse
import com.def.team2.util.imageLoad
import com.def.team2.util.inflate
import kotlinx.android.synthetic.main.item_rank.*
import kotlinx.android.synthetic.main.item_rank_first.*

class RankAdapter(private val callback: Callback) : RecyclerView.Adapter<BaseViewHolder>() {

    interface Callback {
        fun requestVote(data: RankAdapter.Item)
        fun entranceCommunity(data: IdolGroupResponse)
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
            if (data !is IdolGroup) return
            item_rank_first_vote_txt.text = data.currentBallots.size.toString()
            item_rank_first_name_txt.text = data.name
        }

    }

    inner class ViewHolder private constructor(itemView: View) : BaseViewHolder(itemView) {
        constructor(parent: ViewGroup) : this(parent.inflate(R.layout.item_rank, false))

        override fun onDataBind(data: Any?) {
            if (data !is IdolGroup) return
            item_rank_vote_txt.text = data.currentBallots.size.toString()
            item_rank_name_txt.text = data.name
            item_rank_img.imageLoad(data.images[1])
            items.find { it.data.id == data.id }?.apply {
                item_rank_txt.text = context.getString(R.string.str_current_rank, (items.indexOf(this) + 1))
            }
        }

    }

    data class Item(
        val data: IdolGroup,
        val viewType: ViewType
    )

}