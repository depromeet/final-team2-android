package com.def.team2.screen.profile

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import io.reactivex.Observable

interface ProfileContract {

    interface View : BaseRxView<Presenter> {

        enum class Status {
            SCHOOL, IDOL, SETTING
        }

        fun clickImageEdit(): Observable<Unit>
        fun clickSchool(): Observable<Unit>
        fun clickIdol(): Observable<Unit>
        fun clickSetting(): Observable<Unit>

        fun changeFragment(status: Status)

    }

    interface Presenter : BaseRxPresenter {
        fun subscribeMoveFragment()
        fun subscribeEditImage()
    }

}