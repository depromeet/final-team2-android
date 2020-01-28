package com.def.team2.screen.profile.school

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView

interface ProfileSchoolContract {

    interface View : BaseRxView<Presenter> {
        fun setSchoolInfo()
    }

    interface Presenter : BaseRxPresenter {
        fun subscribeSchoolInfo()
    }

}