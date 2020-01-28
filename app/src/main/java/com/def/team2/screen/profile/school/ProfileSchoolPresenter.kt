package com.def.team2.screen.profile.school

import io.reactivex.disposables.CompositeDisposable

class ProfileSchoolPresenter(private val view: ProfileSchoolContract.View) : ProfileSchoolContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()
    override fun start() {
        subscribeSchoolInfo()
    }

    override fun subscribeSchoolInfo() {
        view.setSchoolInfo() //TODO School Data Class 만들기
    }
}