package com.def.team2.screen.signup

import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.def.team2.R
import com.def.team2.util.toast
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.view_signup_email.*
import kotlinx.android.synthetic.main.view_signup_idol.*
import kotlinx.android.synthetic.main.view_signup_nickname.*
import kotlinx.android.synthetic.main.view_signup_password.*
import kotlinx.android.synthetic.main.view_signup_school.*
import java.util.*

class SignUpFragment : Fragment(), SignUpContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: SignUpContract.Presenter

    private val viewStack = Stack<View>()

    private val schoolSearchAdapter: SearchAdapter by lazy {
        SearchAdapter {
            presenter.school.onNext(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifeCycleOwner = this
        setLifecycle()
        presenter = SignUpPresenter(this@SignUpFragment)
        view.requestFocus()

        rv_signup_school_search.apply {
            adapter = schoolSearchAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onPause() {
        super.onPause()
        presenter.clearDisposable()
    }

    override val isActive: Boolean = isAdded

    override val nickname: CharSequence by lazy {
        et_signup_nickname.text
    }
    override val nicknameNextClick: Observable<Unit> by lazy {
        btn_signup_nickname_next.clicks()
    }

    override val email: CharSequence by lazy {
        et_signup_email.text
    }
    override val emailNextClick: Observable<Unit> by lazy {
        btn_signup_email_next.clicks()
    }

    override val password: CharSequence by lazy {
        et_signup_password.text
    }
    override val passwordNextClick: Observable<Unit> by lazy {
        btn_signup_password_next.clicks()
    }

    override val school: CharSequence by lazy {
        et_signup_school.text
    }

    override val schoolChanges: Observable<CharSequence> by lazy {
        et_signup_school.textChanges()
    }

    override val schoolNextClick: Observable<Unit> by lazy {
        btn_signup_school_next.clicks()
    }

    override val backButtonsClick: Observable<Unit> by lazy {
        Observable.merge(
            Observable.merge(
                iv_nickname_back.clicks(),
                iv_email_back.clicks(),
                iv_password_back.clicks()
            ),
            Observable.merge(iv_school_back.clicks(),
                iv_idol_back.clicks()
            )
        )
    }

    override fun showEmailUI() {
        TransitionManager.beginDelayedTransition(view as ViewGroup, Slide(Gravity.END))
        view_signup_email.apply {
            viewStack.push(this)
            visibility = View.VISIBLE
            requestFocus()
        }
    }

    override fun showPasswordUI() {
        TransitionManager.beginDelayedTransition(view as ViewGroup, Slide(Gravity.END))
        view_signup_password.apply {
            viewStack.push(this)
            visibility = View.VISIBLE
            requestFocus()
        }
    }

    override fun showSchoolUI() {
        TransitionManager.beginDelayedTransition(view as ViewGroup, Slide(Gravity.END))
        view_signup_school.apply {
            viewStack.push(this)
            visibility = View.VISIBLE
            requestFocus()
        }
    }

    override fun setSchoolText(school: CharSequence) {
        et_signup_school.setText(school)
    }

    override fun addSchoolList(schools: List<String>) {
        schoolSearchAdapter.setItems(schools)
    }

    override fun setSchoolListVisible(active: Boolean) {
        rv_signup_school_search.apply {
            visibility = if (active) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun showMyIdolUI() {
        TransitionManager.beginDelayedTransition(view as ViewGroup, Slide(Gravity.END))
        view_signup_idol.apply {
            viewStack.push(this)
            visibility = View.VISIBLE
            requestFocus()
        }
    }

    override fun deleteUI() {
        if (viewStack.empty()) {
            activity?.supportFragmentManager?.popBackStack()
        } else {
            viewStack.pop().apply {
                TransitionManager.beginDelayedTransition(view as ViewGroup, Slide(Gravity.END))
                visibility = View.GONE

                // Todo 백버튼 시 focus 조절 필요
            }
        }
    }

    override fun showToast(msg: String) {
        context?.toast(msg)
    }

    companion object {

        const val TAG = "FRAGMENT_SIGN_UP"

        @JvmStatic
        fun newInstance() = SignUpFragment()
    }
}