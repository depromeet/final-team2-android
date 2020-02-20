package com.def.team2.screen.main

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api
import io.reactivex.Observable

interface MainContract {

    interface View : BaseRxView<Presenter> {

        enum class Status{
            RANK,CHAT,PROFILE
        }

        fun getApiProvider(): Api
        fun clickBarRank(): Observable<Unit>
        fun clickBarChat(): Observable<Unit>
        fun clickBarProfile(): Observable<Unit>
        fun changeBar(status: Status)
    }
    interface Presenter : BaseRxPresenter{
        fun subscribeMoveFragment()
        fun subscribeCurrentVote()
    }

}