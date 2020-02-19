package com.def.team2.screen.rank

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api
import com.def.team2.network.model.IdolGroup

interface RankContract {

    interface View : BaseRxView<Presenter> {
        fun getApiProvider(): Api
        fun setRank(data: List<IdolGroup>)
        fun updateVote(data: RankAdapter.Item)
        fun updateDate(formatTimeRemaining: String)
    }

    interface Presenter : BaseRxPresenter {
        fun subscribeRank(ballotIds:Long)
        fun subscribeVote(item:RankAdapter.Item)
        fun subscribeTime()
    }

}