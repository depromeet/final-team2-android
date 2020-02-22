package com.def.team2.screen.chatlist

import com.def.team2.base.UserData
import com.def.team2.screen.chatlist.model.ChatListInfo
import com.def.team2.util.e
import com.def.team2.util.formatTimeRemaining
import com.def.team2.util.getTimeRemaining
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class ChatListPresenter(
    val view: ChatListContract.View,
    val interactor: ChatListInteractor
): ChatListContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        subscribeTime()
    }

    override fun loadChatList() {
        interactor.getChatListInfos()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.showChatList(it)
                view.setVisibilityDefaultError(it.isEmpty())
            }, { error ->
                e("failed to load idol list in chat list, error: " + error.message)
                view.setVisibilityDefaultError(true)
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

    override fun subscribeTime() {
        Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.updateDate(formatTimeRemaining(getTimeRemaining(UserData.currentVote?.endDate
                    ?: "0")))
        }.bindUntilClear()
    }

}