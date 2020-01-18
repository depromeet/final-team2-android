package com.def.team2.screen.signin

import android.util.Log
import com.def.team2.SaveToken
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
                SignInModel(
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
            .flatMapSingle {
                // 로그인 api 처리
                Log.e("요소 확인", "email: ${it.email}, passowrd: ${it.password}")
                return@flatMapSingle Single.just(SignInResponse("abcdefg"))
            }.map {
                // api 결과에서 토큰만 뽑을 것
                it.token
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                // FixMe token 저장 풀 것
//                saveToken.invoke(it)
                view.showMainUI()
            }
            .bindUntilClear()
    }

    override fun subscribePreference() {
        view.getPreference()
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