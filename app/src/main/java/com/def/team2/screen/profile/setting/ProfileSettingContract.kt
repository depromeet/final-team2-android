package com.def.team2.screen.profile.setting

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import io.reactivex.Observable

interface ProfileSettingContract {

    interface View : BaseRxView<Presenter> {
        fun setSetting()
        fun editClick(): Observable<Unit>
    }

    interface Presenter : BaseRxPresenter {
        fun subscribeSetting()
        fun subscribeEdit()
    }

}