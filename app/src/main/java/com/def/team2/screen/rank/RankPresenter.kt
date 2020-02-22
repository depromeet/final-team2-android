package com.def.team2.screen.rank

import com.def.team2.base.UserData
import com.def.team2.network.model.BallotRequest
import com.def.team2.util.formatTimeRemaining
import com.def.team2.util.getTimeRemaining
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class RankPresenter(private val view: RankContract.View) : RankContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        subscribeTime()
        subscribeRank()
    }

    override fun subscribeRank() {
        UserData.school?.let { school ->
            view.getApiProvider().getSchoolRanking(school.id)
                    .map { it.idols }
                    .map { idolGroups -> idolGroups.sortedByDescending { it.currentBallots.size } }
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({
                        view.setRank(it)
                    }, {

                    }).bindUntilClear()
        }
    }

    override fun subscribeVote(item: RankAdapter.Item) {
        val vote = BallotRequest(item.data.id, 0)
        view.getApiProvider().createBallot(vote).subscribe({
            view.updateVote(item)
        }, {

        }).bindUntilClear()
    }

    override fun subscribeTime() {
        Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.updateDate(formatTimeRemaining(getTimeRemaining(UserData.currentVote?.endDate
                    ?: "0")))
        }.bindUntilClear()
    }


}