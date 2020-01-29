package com.def.team2.screen.profile.idol

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import io.reactivex.Observable

interface ProfileIdolContract {

    interface View : BaseRxView<Presenter> {
        fun setIdolList()
        fun deleteClick(): Observable<Unit>
    }

    interface Presenter : BaseRxPresenter {
        fun subscribeIdolList()
        fun subscribeIdolDelete()
        //TODO 시간되면 리스트 이동도 만들 것
    }

}