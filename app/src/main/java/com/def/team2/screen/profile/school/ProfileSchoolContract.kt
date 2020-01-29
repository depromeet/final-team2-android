package com.def.team2.screen.profile.school

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.model.School

interface ProfileSchoolContract {

    interface View : BaseRxView<Presenter> {
        fun setSchoolInfo(school: School)
    }

    interface Presenter : BaseRxPresenter {
        fun subscribeSchoolInfo()
    }

}