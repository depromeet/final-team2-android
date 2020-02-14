package com.def.team2.screen.rank

import com.def.team2.network.model.BallotRequest
import io.reactivex.disposables.CompositeDisposable

class RankPresenter(private val view: RankContract.View) : RankContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
    }

    override fun subscribeRank(ballotIds: Long) {
        view.getApiProvider().getBallots(listOf(ballotIds)).subscribe({
            view.setRank(it.map { data -> data.idol })
        }, {}).bindUntilClear()
    }

    override fun subscribeVote(item: RankAdapter.Item) {
        val vote = BallotRequest(item.data.id, 0)
        view.getApiProvider().createBallot(vote).subscribe({

        }, {

        }).bindUntilClear()
    }
}