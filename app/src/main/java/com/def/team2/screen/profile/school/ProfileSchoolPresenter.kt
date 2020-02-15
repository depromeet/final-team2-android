package com.def.team2.screen.profile.school

import com.def.team2.network.model.Location
import com.def.team2.network.model.School
import io.reactivex.disposables.CompositeDisposable

class ProfileSchoolPresenter(private val view: ProfileSchoolContract.View) : ProfileSchoolContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()
    override fun start() {
        subscribeSchoolInfo()
    }

    override fun subscribeSchoolInfo() {
        view.setSchoolInfo(School(8081, "거창나래학교", "서울 종로구 혜화동", Location(37.59156, 127.000565), School.Level.MIDDLE, 3, ""))
    }
}