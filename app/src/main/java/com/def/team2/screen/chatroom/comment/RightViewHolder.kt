package com.def.team2.screen.chatroom.comment

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R
import com.def.team2.screen.chatroom.model.ChatRoomComment

class RightViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val commentTextView: TextView = itemView.findViewById(R.id.tv_chat_room_right_content)
    private val timeTextView: TextView = itemView.findViewById(R.id.tv_chat_room_right_time)


    fun bind(item: ChatRoomComment) {
        item.run {

            commentTextView.text = comment
            timeTextView.text = time
        }
    }
}