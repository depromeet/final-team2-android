package com.def.team2.signin

interface SignInConract {

    interface View {
        val isActive: Boolean

        fun setLoadingIndicator(active: Boolean)

        fun showSignUpView()

        fun showMainView()
    }

    interface Presenter {
        fun loginwithPassword(email: String, password: String)
    }
}