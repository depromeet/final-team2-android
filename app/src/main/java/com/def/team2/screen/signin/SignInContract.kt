package com.def.team2.screen.signin

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import io.reactivex.Observable

interface SignInContract {

    interface View: BaseRxView<Presenter> {
        val isActive: Boolean

        val signUpTapClick: Observable<Unit>

        val signInClick: Observable<Unit>

        val email: CharSequence

        val password: CharSequence

        fun setLoadingIndicator(active: Boolean)

        fun showSignUpUI()

        fun showMainUI()

        fun showToast(msg: String)
    }

    interface Presenter: BaseRxPresenter {
        fun subscribeSignUpTapClick()

        fun subscribeSignIn()
    }
}