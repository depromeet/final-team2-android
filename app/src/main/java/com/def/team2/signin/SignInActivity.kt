package com.def.team2.signin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.def.team2.R

class SignInActivity : AppCompatActivity(), SignInConract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    override val isActive: Boolean
        get() = true

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showSignUpView() {

    }

    override fun showMainView() {

    }
}
