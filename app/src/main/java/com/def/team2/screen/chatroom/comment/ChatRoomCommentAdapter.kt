package com.def.team2.screen.chatroom.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R
import com.def.team2.screen.chatroom.model.ChatRoomComment

class ChatRoomCommentAdapter(
    private val myId: Long
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = mutableListOf<ChatRoomComment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_RIGHT -> RightViewHolder(parent)
            else -> LeftViewHolder(parent)
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LeftViewHolder -> holder.bind(itemList[position])
            is RightViewHolder -> holder.bind(itemList[position])
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position].id == myId) { VIEW_TYPE_RIGHT } else { VIEW_TYPE_LEFT }
    }

    fun setPrevItems(data: List<ChatRoomComment>) {
        itemList.addAll(data)
        notifyDataSetChanged()
    }

    fun setNextItems(data: List<ChatRoomComment>) {
        itemList.addAll(0, data)
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_LEFT = 0
        const val VIEW_TYPE_RIGHT = 1
    }
}