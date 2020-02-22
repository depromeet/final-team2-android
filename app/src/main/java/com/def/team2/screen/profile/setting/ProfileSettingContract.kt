package com.def.team2.screen.profile.setting

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api
import com.def.team2.network.model.School
import com.def.team2.network.model.User
import com.def.team2.screen.search.SearchFragment
import io.reactivex.Observable

interface ProfileSettingContract {

    interface View : BaseRxView<Presenter> {
        fun setSetting(user:User,school: School)
        val editClick: Observable<Unit>
        val schoolClick: Observable<Unit>
        fun showSearchDialog(dialog:SearchFragment)
        fun getApiProvider(): Api
    }

    interface Presenter : BaseRxPresenter {
        fun subscribeSetting()
        fun subscribeEdit()
        fun subscribeSchool()
    }

}