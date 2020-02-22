package com.def.team2.screen.main

import com.def.team2.base.UserData
import com.def.team2.network.model.BallotRequest
import com.def.team2.network.model.IdolGroup
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
        view.getApiProvider().createBallot(BallotRequest(id, UserData.currentVote?.id
                ?: -1)).subscribe({
            view.showVoteDialog(it.id)
        }, {}).bindUntilClear()
    }

    override fun subscribeLike(data: IdolGroup) {
        val idols = UserData.user?.idols
        idols?.apply {
            val id = this.find { it == data.id }
            if (id == null) idols.add(data.id)
            else idols.remove(id)
        }
        view.getApiProvider().updateUser(UpdateUserRequest(UpdateUserRequest.Data(idols = idols))).subscribe({
            saveToken(it.token)
            UserData.idolList.add(data)
        }, {}).bindUntilClear()
    }

    private fun saveToken(token: String) {
        view.sharedPreferences
                .edit()
                .putString(KEY_TOKEN, token)
                .apply()
    }
}