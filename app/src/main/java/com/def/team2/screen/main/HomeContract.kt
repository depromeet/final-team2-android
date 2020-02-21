package com.def.team2.screen.main

import android.content.SharedPreferences
import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api
import io.reactivex.Observable

interface HomeContract {

    interface View : BaseRxView<Presenter> {

        enum class Type{
            MAP,RANK
        }

        val rankClick: Observable<Unit>
        val mapClick: Observable<Unit>
        val searchClcik: Observable<Unit>
        val sharedPreferences: SharedPreferences

        fun changeType(type:Type)
        fun shpwSearchDialog()
        fun showVoteDialog()
        fun getApiProvider(): Api
    }

    interface Presenter : BaseRxPresenter {

        fun subscribeClick()
        fun subscribeVote(id: Long)
        fun subscribeLike(id: Long)
    }

}