package com.def.team2.screen.chatroom

import com.def.team2.util.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
                e("failed to load comment, error: $it")
                view.showToast("내용을 불러오는데 실패했습니다.")
            })
            .bindUntilClear()
    }

    override fun addComment(comment: String) {
        interactor.sendComment(comment)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { view.deleteSentCommentText() }
            .observeOn(Schedulers.io())
            .switchMap { interactor.getComments() } // Todo 나중에 send 오류랑 getComments 오류랑 구분해야 함
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.refreshCommentList(it)
            }, {
                e("failed to send comment, error: $it")
                view.showToast("오류 발생")
            }).bindUntilClear()

//        view.showToast("임시로 '$comment' 보내기 성공함")
    }
}