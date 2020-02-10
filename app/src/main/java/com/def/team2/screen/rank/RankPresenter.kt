package com.def.team2.screen.rank

import com.def.team2.network.model.BallotRequest
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class RankPresenter(private val view: RankContract.View) : RankContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        subscribeCurrentTime()
        subscribeClicks()
    }

    override fun subscribeRank(ballotIds: String) {
        view.getApiProvider().getBallots(ballotIds).subscribe({
            view.setRank(it.map { data -> data.idol })
        }, {}).bindUntilClear()
    }

    override fun subscribeCurrentTime() {
        Observable.interval(1, TimeUnit.SECONDS).subscribe {
            view.updateTime("")
        }.bindUntilClear()

    }

    override fun subscribeVote(item: RankAdapter.Item) {
//        val vote = BallotRequest(,item.data.id,)
        view.getApiProvider().createBallot(vote).subscribe({

        },{

        }).bindUntilClear()
    }

    override fun subscribeClicks() {
        view.mapClick.subscribe {

        }.bindUntilClear()
        view.searchClick.subscribe {
            view.showSearchDialog()
        }.bindUntilClear()
    }
}