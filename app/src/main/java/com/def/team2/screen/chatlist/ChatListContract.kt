package com.def.team2.screen.chatlist

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.screen.chatlist.model.ChatListInfo

interface ChatListContract {
    interface View: BaseRxView<Presenter> {

        val isActive: Boolean

        fun showChatList(chatListInfos: List<ChatListInfo>)

        fun setVisibilityDefaultError(isActive: Boolean)

        fun showChatRoomUI(idolId: Long)

        fun showSearchUI()

        fun showVotePopUp()

        fun showToast(msg: String)
    }

    interface Presenter: BaseRxPresenter {

        fun openSearchIdol()

        fun loadChatList()

        fun updateChatList()

        fun voteIdol(idolId: Long)

        fun openChatRoom(chatListInfo: ChatListInfo)
    }
}