package com.def.team2.screen.signin

import android.util.Log
import com.def.team2.util.isEmail
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class SignInPresenter(
    private val view: SignInContract.View,
    private val signInInteractor: SignInInteractor
) : SignInContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    private val isLoading = BehaviorSubject.createDefault(false)

    override fun start() {
        subscribeSignUpTapClick()
        subscribeSignIn()
    }

    fun subscribeLoadingState() {

    }

    override fun subscribeSignUpTapClick() {
        view.signUpTapClick
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                view.showSignUpUI()
            }, {
                Log.e("SignInPresenter", "SignUpClick Error")
            })
            .bindUntilClear()
    }

    override fun subscribeSignIn() {
        view.signInClick
            .filter {
                if (view.email.isEmail()) {
                    return@filter true
                }
                view.showToast("Email is Invalid")
                return@filter false
            }
            .filter {
                if (view.password.isNotEmpty()) {
                    return@filter true
                }
                view.showToast("Password is Invalid")
                return@filter false
            }
            .switchMapSingle {
                signInInteractor.signIn(view.email.toString(), view.password.toString())
            }.observeOn(AndroidSchedulers.mainThread())
            .retry { _, e ->
                Log.e("signIn error", "error, message: ${e.message}")
                view.showToast("Failed to Login, Please Check Id or PW")
                true
            }
            .doOnNext { signInInteractor.saveToken(it) }
            .switchMap { signInInteractor.savedTokenChanges() }
            .flatMapSingle { signInInteractor.setMyInfo() }
            .observeOn(AndroidSchedulers.mainThread())
            .retry { count, e ->
                Log.e("error", "count: $count, error, message: ${e.message}")
                view.showToast("다시 로그인해주세요")
                true
            }
            .subscribe{ view.showMainUI() }
            .bindUntilClear()
    }
}