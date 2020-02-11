package com.def.team2.screen.chatlist

import io.reactivex.disposables.CompositeDisposable

class ChatListPresenter(
    val view: ChatListContract.View,
    val interactor: ChatListInteractor
): ChatListContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {

    }

    override fun loadChatList() {

    }

    override fun updateChatList() {

    }

    override fun voteIdol(idolId: Long) {

    }

    override fun openChatRoom(idolId: Long) {

    }
}