package com.def.team2.screen.rank

import com.def.team2.base.UserData
import com.def.team2.network.model.BallotRequest
import io.reactivex.disposables.CompositeDisposable

class RankPresenter(private val view: RankContract.View) : RankContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
    }

    override fun subscribeRank(ballotIds: String) {
        view.getApiProvider().getBallots(ballotIds).subscribe({
            view.setRank(it.map { data -> data.idol })
        }, {}).bindUntilClear()
    }

    override fun subscribeVote(item: RankAdapter.Item) {
        val vote = BallotRequest(UserData.user!!.id,item.data.id, 0)
        view.getApiProvider().createBallot(vote).subscribe({

        }, {

        }).bindUntilClear()
    }
}