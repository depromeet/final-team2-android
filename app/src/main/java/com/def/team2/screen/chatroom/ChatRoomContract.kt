package com.def.team2.screen.chatroom

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.screen.chatroom.model.ChatRoomComment

interface ChatRoomContract {

    interface View: BaseRxView<Presenter> {

        fun addPrevCommentList(chatRoomComment: List<ChatRoomComment>)

        fun addNextCommentList(chatRoomComment: List<ChatRoomComment>)

        fun refreshCommentList(chatRoomComment: List<ChatRoomComment>)

        fun deleteSentCommentText()

        fun showToast(msg: String)
    }

    interface Presenter: BaseRxPresenter {
        fun startWithIdolId(idolId: Long)

        fun loadPrevComment()

        fun loadNextComment()

        fun addComment(comment: String)
    }
}