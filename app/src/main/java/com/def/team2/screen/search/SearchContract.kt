package com.def.team2.screen.search

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api

interface SearchContract {

    interface View : BaseRxView<Presenter>{

        fun getApiProvider(): Api
        fun chageEditText()
        fun setSearchResponse()

    }

    interface Presenter : BaseRxPresenter{

        fun subscribeSearch()
    }

}