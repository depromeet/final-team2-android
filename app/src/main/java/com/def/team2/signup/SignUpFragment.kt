package com.def.team2.signup

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
import com.def.team2.util.KEY_TOKEN
import com.def.team2.util.sharedPreferences
import com.def.team2.util.toast
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
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
            schoolSelect.onNext(it)
        }
    }

    private val idolSearchAdapter: SearchAdapter by lazy {
        SearchAdapter {
            idolSelect.onNext(it)
        }
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

        rv_signup_idol_search.apply {
            adapter = idolSearchAdapter
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

    override val schoolSelect: PublishSubject<Pair<String, String>> = PublishSubject.create()

    override val schoolChanges: Observable<CharSequence> by lazy {
        et_signup_school.textChanges()
    }

    override val schoolNextClick: Observable<Unit> by lazy {
        btn_signup_school_next.clicks()
    }

    override val idol: CharSequence by lazy {
        et_signup_idol.text
    }

    override val idolSelect: PublishSubject<Pair<String, String>> = PublishSubject.create()

    override val idolChanges: Observable<CharSequence> by lazy {
        et_signup_idol.textChanges()
    }

    override val signUpClick: Observable<Unit> by lazy {
        btn_signup.clicks()
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

    override val preferenceChanges: Observable<String> by lazy {
        RxSharedPreferences.create(context!!.sharedPreferences())
            .getString(KEY_TOKEN).asObservable()
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

    override fun addSchoolList(schools: List<Pair<String, String>>) {
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

    override fun setIdolText(idol: CharSequence) {
        et_signup_idol.setText(idol)
    }

    override fun addIdolList(idols: List<Pair<String, String>>) {
        idolSearchAdapter.setItems(idols)
    }

    override fun setIdolListVisible(active: Boolean) {
        rv_signup_idol_search.apply {
            visibility = if (active) {
                View.VISIBLE
            } else {
                View.GONE
            }
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
