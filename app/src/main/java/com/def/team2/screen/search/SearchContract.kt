package com.def.team2.screen.search

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api
import io.reactivex.Observable

interface SearchContract {

    interface View : BaseRxView<Presenter> {

        val schoolChanges: Observable<CharSequence>
        val closeClick: Observable<Unit>

        fun getApiProvider(): Api
        fun setSearchResponse(data:List<Any>)
        fun adapterClear()
        fun dismissDialog()

    }

    interface Presenter : BaseRxPresenter {

        fun subscribeSearch()
        fun subscribeClose()

    }

}