package com.def.team2.screen.main

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainPresenter(private val view: MainContract.View) : MainContract.Presenter {
    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun subscribeMoveFragment() {
        view.clickBarRank()
                .doOnError { Log.e("MainPresenter", "clickBarRank Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.changeBar(MainContract.View.Status.RANK) }
                .bindUntilClear()
        view.clickBarChat()
                .doOnError { Log.e("MainPresenter", "clickBarChat Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.changeBar(MainContract.View.Status.CHAT) }
                .bindUntilClear()
        view.clickBarProfile()
                .doOnError { Log.e("MainPresenter", "clickBarProfile Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.changeBar(MainContract.View.Status.PROFILE) }
                .bindUntilClear()
    }

    override fun start() {
        subscribeMoveFragment()
        view.changeBar(MainContract.View.Status.RANK)
    }

}