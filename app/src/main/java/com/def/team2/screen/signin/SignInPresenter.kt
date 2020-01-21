package com.def.team2.screen.signin

import android.util.Log
import com.def.team2.SaveToken
import com.def.team2.network.model.SignInRequest
import com.def.team2.util.isEmail
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class SignInPresenter(
    private val view: SignInContract.View,
    private val saveToken: SaveToken
) : SignInContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    private val isLoading = BehaviorSubject.createDefault(false)

    override fun start() {
        subscribeSignUpTapClick()
        subscribeSignIn()
        subscribePreference()
    }

    fun subscribeLoadingState() {

    }

    override fun subscribeSignUpTapClick() {
        view.getSignUpTapClicks()
            .doOnError { Log.e("SignInPresenter", "SignUpClick Error") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { view.showSignUpUI() }
            .bindUntilClear()
    }

    override fun subscribeSignIn() {

        view.getSignInButtonClicks()
            .map {
                SignInRequest(
                    view.getEmailText().toString(),
                    view.getPasswordText().toString()
                )
            }
            .filter {
                if (it.email.isEmail()) {
                    return@filter true
                }
                view.showToast("Email is Invalid")
                return@filter false
            }
            .filter {
                if (it.password.isNotEmpty()) {
                    return@filter true
                }
                view.showToast("Password is Invalid")
                return@filter false
            }
            .observeOn(Schedulers.io())
            .switchMapSingle {
                Log.e("요소 확인", "email: ${it.email}, passowrd: ${it.password}")
                view.getApiProvider()
                    .signIn(it)
            }.observeOn(AndroidSchedulers.mainThread())
            .retry { _, e ->
                Log.e("error", "error, message: ${e.message}")
                view.showToast("Failed to Login, Please Check Id or PW")
                true
            }
            .subscribe {
                saveToken.invoke(it.token)
//                view.showMainUI()
            }
            .bindUntilClear()
    }

    override fun subscribePreference() {
        view.preferenceChanges
            .filter { it.isNotEmpty() }
            .flatMapSingle {
                // MyInfo 가져온다.
                return@flatMapSingle Single.just("myInfo 올꺼임")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.showMainUI()
            }
            .bindUntilClear()
    }
}