package com.def.team2.screen.signin

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.base.BaseActivity
import com.def.team2.screen.main.MainActivity
import com.def.team2.screen.signup.SignUpFragment
import com.def.team2.util.sharedPreferences
import com.def.team2.util.throttleClicks
import com.def.team2.util.toast
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity(), SignInContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: SignInContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // FixMe 저장된 sharedPreference 제거용
        this.sharedPreferences().edit().clear().commit()

        lifeCycleOwner = this
        setLifecycle()
        presenter = SignInPresenter(this@SignInActivity, SignInInteractor(this)).apply {
            start()
        }
    }

    override val isActive: Boolean
        get() = !isFinishing

    override val signUpTapClick: Observable<Unit> by lazy { tv_signin_tab_signup.throttleClicks() }

    override val signInClick: Observable<Unit> by lazy { btn_signin.throttleClicks() }

    override val email: CharSequence by lazy { et_email.text }

    override val password: CharSequence by lazy { et_password.text }

    override fun setLoadingIndicator(active: Boolean) {

    }

    override fun showSignUpUI() {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.signup_fragment_slide_from_bottom,
                R.anim.signup_fragment_slide_to_top,
                R.anim.signup_fragment_slide_from_top,
                R.anim.signup_fragment_slide_to_bottom
            )
        }.replace(R.id.fl_signup_content, SignUpFragment.newInstance(), SignUpFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun showMainUI() {
        val intent = Intent(this@SignInActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showToast(msg: String) {
        this.toast(msg)
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentByTag(SignUpFragment.TAG)?.let {
            (it as SignUpFragment).deleteUI()
        } ?: kotlin.run {
            super.onBackPressed()
        }
    }
}
