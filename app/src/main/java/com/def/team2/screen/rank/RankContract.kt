package com.def.team2.screen.rank

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api

interface RankContract {

    interface View : BaseRxView<Presenter>{

        fun setRank()
        fun updateTime()
        fun updateVote()
        fun getApiProvider(): Api
    }

    interface Presenter : BaseRxPresenter{

        fun subscribeRank()
        fun subscribeCurrentTime()
        fun subscribeVote()

    }

}