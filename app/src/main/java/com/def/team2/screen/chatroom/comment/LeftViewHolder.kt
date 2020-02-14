package com.def.team2.screen.chatroom.comment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.def.team2.R
import com.def.team2.screen.chatroom.model.ChatRoomComment

class LeftViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val profileImageView: ImageView = itemView.findViewById(R.id.iv_chat_room_left_profile)
    private val nameTextView: TextView = itemView.findViewById(R.id.tv_chat_room_left_name)
    private val commentTextView: TextView = itemView.findViewById(R.id.tv_chat_room_left_content)
    private val timeTextView: TextView = itemView.findViewById(R.id.tv_chat_room_left_time)


    fun bind(item: ChatRoomComment) {
        item.run {
            profileUrl?.let {
                Glide.with(itemView)
                    .load(it)
                    .into(profileImageView)
            }

            nameTextView.text = name
            commentTextView.text = comment
            timeTextView.text = time
        }
    }
}