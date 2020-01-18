package com.def.team2.signup

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface SignUpContract {

    interface View: BaseRxView<Presenter> {

        val isActive: Boolean

        val nickname: CharSequence

        val nicknameNextClick: Observable<Unit>

        val email: CharSequence

        val emailNextClick: Observable<Unit>

        val password: CharSequence

        val passwordNextClick: Observable<Unit>

        val school: CharSequence

        val schoolChanges: Observable<CharSequence>

        val schoolNextClick: Observable<Unit>

        val backButtonsClick: Observable<Unit>

        fun showEmailUI()

        fun showPasswordUI()

        fun showSchoolUI()

        fun setSchoolText(school: CharSequence)

        fun addSchoolList(schools: List<String>)

        fun setSchoolListVisible(active: Boolean)

        fun showMyIdolUI()

        fun deleteUI()

        fun showToast(msg: String)
    }

    interface Presenter: BaseRxPresenter {

        val school: PublishSubject<CharSequence>

        fun subscribeNickName()

        fun subscribeEmail()

        fun subscribePassword()

        fun subscribeSchool()

        fun subscribeIdol()

        fun subscribeBack()
    }
}