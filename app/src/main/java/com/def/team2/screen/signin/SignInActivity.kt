package com.def.team2.screen.signin

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.SaveToken
import com.def.team2.base.BaseActivity
import com.def.team2.screen.main.MainActivity
import com.def.team2.screen.signup.SignUpFragment
import com.def.team2.util.KEY_TOKEN
import com.def.team2.util.sharedPreferences
import com.def.team2.util.throttleClicks
import com.def.team2.util.toast
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity(), SignInContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: SignInContract.Presenter
    private val rxPreferences by lazy {
        RxSharedPreferences.create(this.sharedPreferences())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        lifeCycleOwner = this
        setLifecycle()
        presenter = SignInPresenter(this@SignInActivity, SaveToken(this)).apply {
            start()
        }
    }

    override val isActive: Boolean
        get() = !isFinishing

    override fun getSignUpTapClicks(): Observable<Unit> = tv_signin_tab_signup.throttleClicks()

    override fun getSignInButtonClicks(): Observable<Unit> = btn_signin.throttleClicks()

    override fun getEmailText(): CharSequence = et_email.text

    override fun getPasswordText(): CharSequence = et_password.text

    override fun getPreference(): Observable<String> =
        rxPreferences.getString(KEY_TOKEN).asObservable()

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
