package com.def.team2.screen.chatroom

import com.def.team2.util.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ChatRoomPresenter(
    val view: ChatRoomContract.View,
    val interactor: ChatRoomInteractor
): ChatRoomContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        loadNextComment()
    }

    override fun startWithIdolId(idolId: Long) {
        interactor.updateIdolId(idolId)
        start()
    }

    override fun loadPrevComment() {

    }

    override fun loadNextComment() {
        interactor
            .getComments()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.addNextCommentList(it)
            },{
                e("failed to load comment, error: ${it}")
                view.showToast("내용을 불러오는데 실패했습니다.")
            })
            .bindUntilClear()
    }

    override fun addComment(comment: String) {
        view.deleteSendedCommentText()

        view.showToast("임시로 '$comment' 보내기 성공함")
    }
}