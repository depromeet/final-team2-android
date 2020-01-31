package com.def.team2.screen.rank

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView

interface RankContract {

    interface View : BaseRxView<Presenter>{

        fun setRank()
        fun updateTime()
        fun updateVote()

    }

    interface Presenter : BaseRxPresenter{

        fun subscribeRank()
        fun subscribeCurrentTime()
        fun subscribeVote()

    }

}