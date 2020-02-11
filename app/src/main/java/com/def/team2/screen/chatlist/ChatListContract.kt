package com.def.team2.screen.chatlist

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.screen.chatlist.model.ChatListInfo

interface ChatListContract {
    interface View: BaseRxView<Presenter> {

        val isActive: Boolean

        fun showChatList(chatListInfos: List<ChatListInfo>)

        fun showChatRoomUI(idolId: Long)

        fun showVotePopUp()

        fun showToast(msg: String)
    }

    interface Presenter: BaseRxPresenter {

        fun loadChatList()

        fun updateChatList()

        fun voteIdol(idolId: Long)

        fun openChatRoom(idolId: Long)
    }
}