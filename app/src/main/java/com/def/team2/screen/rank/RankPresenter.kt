package com.def.team2.screen.rank

import io.reactivex.disposables.CompositeDisposable

class RankPresenter(private val view: RankContract.View) : RankContract.Presenter{

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        subscribeRank()
        subscribeCurrentTime()
    }

    override fun subscribeRank() {

    }

    override fun subscribeCurrentTime() {

    }

    override fun subscribeVote() {

    }
}