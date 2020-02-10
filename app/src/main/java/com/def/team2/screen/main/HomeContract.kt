package com.def.team2.screen.main

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import io.reactivex.Observable

interface HomeContract {

    interface View : BaseRxView<Presenter> {

        enum class Type{
            BLACK,WHITE
        }

        val rankClick: Observable<Unit>
        val mapClick: Observable<Unit>

        fun changeType(type:Type)

    }

    interface Presenter : BaseRxPresenter {

        fun subscribeClick()

    }

}