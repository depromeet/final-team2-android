package com.def.team2.screen.chatlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.def.team2.R
import com.def.team2.screen.chatlist.model.ChatListInfo

class ChatListAdapter(
    private val viewClickCallback: (item: ChatListInfo) -> Unit,
    private val voteClickCallback: (idolId: Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = mutableListOf<ChatListInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.item_chat_list_idol, parent, false),
            viewClickCallback,
            voteClickCallback
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(itemList[position])
    }

    fun setItems(data: List<ChatListInfo>) {
        itemList.clear()
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(
        itemView: View,
        viewClickCallback: (item: ChatListInfo) -> Unit,
        voteClickCallback: (idolId: Long) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val idolNameTextView: TextView = itemView.findViewById(R.id.tv_chat_list_vote_total)
        private val idolTotalVoteTextView: TextView = itemView.findViewById(R.id.tv_chat_list_name)
        private val idolImageView: ImageView = itemView.findViewById(R.id.iv_chat_list_background)
        private val voteImageView: ImageView = itemView.findViewById(R.id.iv_chat_list_vote)

        private var chatListInfo: ChatListInfo? = null

        init {

            itemView.setOnClickListener { _ ->
                chatListInfo?.let {
                    viewClickCallback.invoke(it)
                }
            }

            voteImageView.setOnClickListener { _ ->
                chatListInfo?.let {
                    voteClickCallback.invoke(it.id)
                }
            }
        }

        fun bind(item: ChatListInfo) {
            this.chatListInfo = item.apply {
                Glide.with(itemView)
                    .load(imgUrl)
                    .into(idolImageView)

                idolTotalVoteTextView.text = item.name
                idolNameTextView.text = "${totalVote}명의 픽"

                if (!userIsVoted) {
                    voteImageView.visibility = View.VISIBLE
                }
            }
        }
    }
}