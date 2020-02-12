package com.def.team2.screen.chatroom

import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.def.team2.R
import com.def.team2.base.BaseActivity
import com.def.team2.base.UserData
import com.def.team2.screen.chatroom.comment.ChatRoomCommentAdapter
import com.def.team2.screen.chatroom.model.ChatRoomComment
import com.def.team2.util.EXTRA_IDOL_IMAGE_URL
import com.def.team2.util.EXTRA_IDOL_NAME
import com.def.team2.util.EXTRA_IDOL_id
import com.def.team2.util.toast
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : BaseActivity(), ChatRoomContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: ChatRoomContract.Presenter

    private val chatRoomCommentAdapter: ChatRoomCommentAdapter by lazy {
        ChatRoomCommentAdapter(
            UserData.user?.id?.toLong() ?: -1
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        lifeCycleOwner = this
        setLifecycle()
        presenter = ChatRoomPresenter(this@ChatRoomActivity, ChatRoomInteractor(this)).apply {
            startWithIdolId(intent.getLongExtra(EXTRA_IDOL_id, -1))
        }

        initView()
        initListener()
    }

    private fun initView() {

        val idolName = intent.getStringExtra(EXTRA_IDOL_NAME)
        val idolImgUrl = intent.getStringExtra(EXTRA_IDOL_IMAGE_URL) ?: ""

        Glide.with(this)
            .load(idolImgUrl)
            .into(iv_chat_room_title)
        tv_chat_room_name.text = idolName

        rv_chat_room_comment.apply {
            adapter = chatRoomCommentAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initListener() {
        iv_chat_room_back.setOnClickListener {
            finish()
        }

        iv_chat_room_comment_send.setOnClickListener {
            presenter.addComment(et_chat_room_comment.text.toString())
        }
    }

    override fun addPrevCommentList(chatRoomComment: List<ChatRoomComment>) {
        chatRoomCommentAdapter.setPrevItems(chatRoomComment)
    }

    override fun addNextCommentList(chatRoomComment: List<ChatRoomComment>) {
        chatRoomCommentAdapter.setNextItems(chatRoomComment)
    }

    override fun deleteSendedCommentText() {
        et_chat_room_comment.setText("")
    }

    override fun showToast(msg: String) {
        toast(msg)
    }
}
