package com.def.team2.screen.signin

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.Api
import io.reactivex.Observable

interface SignInContract {

    interface View: BaseRxView<Presenter> {
        val isActive: Boolean

        val preferenceChanges: Observable<String>

        fun getApiProvider(): Api

        fun getSignUpTapClicks(): Observable<Unit>

        fun getSignInButtonClicks(): Observable<Unit>

        fun getEmailText(): CharSequence

        fun getPasswordText(): CharSequence

        fun setLoadingIndicator(active: Boolean)

        fun showSignUpUI()

        fun showMainUI()

        fun showToast(msg: String)
    }

    interface Presenter: BaseRxPresenter {
        fun subscribeSignUpTapClick()

        fun subscribeSignIn()

        fun subscribePreference()
    }
}