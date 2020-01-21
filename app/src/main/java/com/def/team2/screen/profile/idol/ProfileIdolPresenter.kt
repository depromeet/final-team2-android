package com.def.team2.screen.profile.idol

import com.def.team2.util.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ProfileIdolPresenter(private val view: ProfileIdolContract.View) : ProfileIdolContract.Presenter {


    override val disposables: CompositeDisposable = CompositeDisposable()
    override fun start() {
        subscribeIdolList()
        subscribeIdolDelete()
    }

    override fun subscribeIdolList() {
        view.setIdolList() // TODO IDOL List 넘겨주기
    }

    override fun subscribeIdolDelete() {
        view.deleteClick()
                .doOnError { e("deleteClick Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  }
                .bindUntilClear()

    }
}