package com.def.team2.screen.main

import io.reactivex.disposables.CompositeDisposable

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        subscribeClick()
    }

    override fun subscribeClick() {
        view.rankClick.subscribe {
            view.changeType(HomeContract.View.Type.BLACK)
        }.bindUntilClear()
        view.mapClick.subscribe {
            view.changeType(HomeContract.View.Type.WHITE)
        }.bindUntilClear()
    }

}