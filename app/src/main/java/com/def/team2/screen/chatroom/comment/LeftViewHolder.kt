package com.def.team2.screen.chatroom.comment

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.def.team2.R
import com.def.team2.base.BaseViewHolder
import com.def.team2.screen.chatroom.model.ChatRoomComment
import com.def.team2.util.imageLoad
import com.def.team2.util.inflate
import kotlinx.android.synthetic.main.item_chat_room_left.*

class LeftViewHolder private constructor(itemView: View) : BaseViewHolder(itemView) {
    constructor(parent: ViewGroup) : this(parent.inflate(R.layout.item_chat_room_left, false))

    fun bind(item: ChatRoomComment) {
        item.run {
            profileUrl?.let {
                Glide.with(itemView)
                        .load(it)
                        .into(iv_chat_room_left_profile)
            } ?: iv_chat_room_left_profile.imageLoad(R.drawable.profile_default)

            tv_chat_room_left_name.text = name
            tv_chat_room_left_content.text = comment
            tv_chat_room_left_time.text = time
        }
    }
}