package com.def.team2.screen.main

import com.def.team2.base.UserData
import com.def.team2.network.model.BallotRequest
import com.def.team2.network.model.UpdateUserRequest
import com.def.team2.util.KEY_TOKEN
import io.reactivex.disposables.CompositeDisposable

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        subscribeClick()
        view.changeType(HomeContract.View.Type.MAP)
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

    override fun subscribeVote(id: Long) {
        view.getApiProvider().createBallot(BallotRequest(id, UserData.user?.id ?: -0)).subscribe({
            view.showVoteDialog()
        }, {}).bindUntilClear()
    }

    override fun subscribeLike(id: Long) {
        val idols = UserData.user?.idols?.toMutableList()
        idols?.add(id)
        view.getApiProvider().updateUser(UpdateUserRequest(UpdateUserRequest.Data(idols = idols))).subscribe({
            saveToken(it.token)
        },{}).bindUntilClear()
    }
    private fun saveToken(token: String) {
        view.sharedPreferences
                .edit()
                .putString(KEY_TOKEN, token)
                .apply()
    }
}