package com.def.team2.screen.rank

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api
import com.def.team2.network.model.Idol
import com.def.team2.network.model.IdolGroup
import io.reactivex.Observable

interface RankContract {

    interface View : BaseRxView<Presenter> {

        val mapClick: Observable<Unit>
        val searchClick: Observable<Unit>

        fun showSearchDialog()
        fun getApiProvider(): Api
        fun setRank(data: List<IdolGroup>)
        fun updateTime(date: String)
        fun updateVote(data: RankAdapter.Item)
    }

    interface Presenter : BaseRxPresenter {

        fun subscribeClicks()
        fun subscribeRank(ballotIds:String)
        fun subscribeCurrentTime()
        fun subscribeVote(item:RankAdapter.Item)

    }

}