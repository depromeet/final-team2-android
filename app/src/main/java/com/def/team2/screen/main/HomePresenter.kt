package com.def.team2.screen.main

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        subscribeClick()
        view.changeType(HomeContract.View.Type.RANK)
    }

    override fun subscribeClick() {
        view.rankClick.subscribe {
            view.changeType(HomeContract.View.Type.RANK)
        }.bindUntilClear()
        view.mapClick.subscribe {
            view.changeType(HomeContract.View.Type.MAP)
        }.bindUntilClear()
        view.searchClcik.subscribe {
            view.shpwSearchDialog()
        }.bindUntilClear()
    }

    override fun subscribeTime() {
        Observable.interval(1, TimeUnit.SECONDS).subscribe {
            view.updateDate("")
        }.bindUntilClear()
    }
}