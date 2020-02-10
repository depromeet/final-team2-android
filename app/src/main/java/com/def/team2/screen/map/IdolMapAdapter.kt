package com.def.team2.screen.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.def.team2.R
import com.def.team2.screen.map.model.RankIdol

class IdolMapAdapter(
    private val itemClickCallback: (item: RankIdol) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = mutableListOf<RankIdol>()

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

    fun setItems(data: List<RankIdol>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        itemView: View,
        clickCallback: (item: RankIdol) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private var schoolTextView: TextView
        private var schoolAddressTextView: TextView
        private var idolImageView: ImageView
        private var idolNameTextView: TextView
        private var idolRankTextView: TextView

        private var rankIdol: RankIdol? = null

        init {
            schoolTextView = itemView.findViewById(R.id.vertical_tv_map_idol_school)
            schoolAddressTextView = itemView.findViewById(R.id.vertical_tv_map_idol_address)
            idolImageView = itemView.findViewById(R.id.iv_map_idol)
            idolNameTextView = itemView.findViewById(R.id.tv_map_idol_name)
            idolRankTextView = itemView.findViewById(R.id.tv_map_idol_rank)

            itemView.setOnClickListener { _ ->
                rankIdol?.let {
                    clickCallback.invoke(it)
                }
            }
        }

        fun bind(rankIdol: RankIdol) {
            this.rankIdol = rankIdol.apply {
                Glide.with(itemView)
                    .load(rankIdol.imgUrl)
                    .into(idolImageView)

                schoolTextView.text = schoolName
                schoolAddressTextView.text = schoolAddress
                idolNameTextView.text = groupName
                idolRankTextView.text = ranking.toString()
            }
        }
    }
}