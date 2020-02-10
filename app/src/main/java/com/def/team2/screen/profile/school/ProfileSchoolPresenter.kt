package com.def.team2.screen.profile.school

import com.def.team2.base.UserData
import io.reactivex.disposables.CompositeDisposable

class ProfileSchoolPresenter(private val view: ProfileSchoolContract.View) : ProfileSchoolContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()
    override fun start() {
        subscribeSchoolInfo()
    }

    override fun subscribeSchoolInfo() {

    }
}