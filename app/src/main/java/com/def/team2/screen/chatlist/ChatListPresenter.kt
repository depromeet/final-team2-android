package com.def.team2.screen.chatlist

import com.def.team2.screen.chatlist.model.ChatListInfo
import com.def.team2.util.e
import io.reactivex.disposables.CompositeDisposable

class ChatListPresenter(
    val view: ChatListContract.View,
    val interactor: ChatListInteractor
): ChatListContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {

    }

    override fun loadChatList() {
        interactor.getChatListInfos()
            .subscribe({
                view.showChatList(it)
                view.setVisibilityDefaultError(it.isNotEmpty())
            }, { error ->
                e("failed to load idol list in chat list, error: " + error.message)
                view.showToast("내 아이돌 리스트를 가져오는 데 실패했습니다.")
            }).bindUntilClear()
    }

    override fun openSearchIdol() {

    }

    override fun updateChatList() {

    }

    override fun voteIdol(idolId: Long) {

    }

    override fun openChatRoom(chatListInfo: ChatListInfo) {
        view.showChatRoomUI(chatListInfo)
    }
}