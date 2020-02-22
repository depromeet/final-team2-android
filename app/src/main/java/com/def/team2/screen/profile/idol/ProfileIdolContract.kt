package com.def.team2.screen.profile.idol

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api
import com.def.team2.network.model.IdolGroup
import io.reactivex.Observable

interface ProfileIdolContract {

    interface View : BaseRxView<Presenter> {
        fun setIdolList(list: List<IdolGroup>)
        fun deleteClick(): Observable<Unit>
        fun getApiProvider(): Api
    }

    interface Presenter : BaseRxPresenter {
        fun subscribeIdolList()
        fun subscribeIdolDelete()
    }

}