package com.def.team2.screen.chatroom.comment

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.def.team2.R
import com.def.team2.base.BaseViewHolder
import com.def.team2.screen.chatroom.model.ChatRoomComment
import com.def.team2.util.inflate
import kotlinx.android.synthetic.main.item_chat_room_right.*

class RightViewHolder  private constructor(itemView: View) : BaseViewHolder(itemView) {
    constructor(parent: ViewGroup) : this(parent.inflate(R.layout.item_chat_room_right, false))

    fun bind(item: ChatRoomComment) {
        item.run {

            tv_chat_room_right_content.text = comment
            tv_chat_room_right_time.text = time
        }
    }
}