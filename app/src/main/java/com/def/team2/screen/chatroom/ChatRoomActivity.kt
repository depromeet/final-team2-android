package com.def.team2.screen.chatroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.def.team2.R
import com.def.team2.util.EXTRA_IDOL_IMAGE_URL
import com.def.team2.util.EXTRA_IDOL_NAME
import com.def.team2.util.EXTRA_IDOL_id
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val idolId = intent.getLongExtra(EXTRA_IDOL_id, -1)
        val idolName = intent.getStringExtra(EXTRA_IDOL_NAME) ?: ""
        val idolImgUrl = intent.getStringExtra(EXTRA_IDOL_IMAGE_URL) ?: ""

        Glide.with(this)
            .load(idolImgUrl)
            .into(iv_chat_room_title)
        tv_chat_room_name.text = idolName
    }
}
