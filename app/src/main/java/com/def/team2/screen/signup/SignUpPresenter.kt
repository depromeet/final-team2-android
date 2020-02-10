package com.def.team2.screen.signup

import android.util.Log
import com.def.team2.network.model.IdolDto
import com.def.team2.network.model.Location
import com.def.team2.network.model.School
import com.def.team2.util.isEmail
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import retrofit2.HttpException

class SignUpPresenter(
    val view: SignUpContract.View,
    private val signUpInteractor: SignUpInteractor
) : SignUpContract.Presenter {

    override fun start() {
        subscribeNickName()
        subscribeEmail()
        subscribePassword()
        subscribeSchool()
        subscribeIdol()
        subscribeSignUp()
        subscribeBack()
    }

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun subscribeNickName() {
        view.nicknameNextClick
            .map { view.nickname }
            .filter {
                if (it.isNotEmpty()) {
                    return@filter true
                }
                view.showToast("Invalid Nickname")
                return@filter false
            }
            .subscribe {
                view.showEmailUI()
            }.bindUntilClear()
    }

    override fun subscribeEmail() {
        view.emailNextClick
            .map { view.email }
            .filter { email ->
                if (email.isEmail()) {
                    return@filter true
                }
                view.showToast("Invalid Email")
                return@filter false
            }
            .switchMapSingle { signUpInteractor.checkEmailValidate(it.toString()) }
            .observeOn(AndroidSchedulers.mainThread())
            .retry { _, e ->
                if (e is HttpException) {
                    if (e.code() == 403) {
                        view.showToast("Email is Duplicated")
                    } else {
                        view.showToast("Unknown Error")
                    }
                }
                true
            }
            .subscribe { view.showPasswordUI() }
            .bindUntilClear()
    }

    override fun subscribePassword() {
        view.passwordNextClick
            .map { view.password }
            .filter {password ->
                if (password.isNotEmpty()) {
                    return@filter true
                }
                view.showToast("Invalid Password")
                return@filter false
            }
            .subscribe {
                view.showSchoolUI()
            }.bindUntilClear()
    }

    override fun subscribeSchool() {

        subscribeSchoolChanges()
        view.schoolSelect.onNext(School(0, "", "", Location(0.0,0.0), School.Level.ELEMENT, ""))

        view.schoolNextClick
            .map { Pair(view.school, signUpInteractor.schoolId) }
            .filter {
                if (it.first.isNotEmpty() && it.second != null) {
                    return@filter true
                }
                view.showToast("Invalid School")
                return@filter false
            }
            .subscribe { view.showMyIdolUI() }
            .bindUntilClear()

    }

    private fun subscribeSchoolChanges() {

        Observable.merge(view.schoolChanges, view.schoolSelect)
            .doOnNext {
                if (it is School) {
                    view.setSchoolListVisible(false)
                    view.setSchoolText(it.name)
                    signUpInteractor.schoolId = it.id
                }
            }.filter { it is CharSequence }
            .withLatestFrom(
                view.schoolSelect,
                BiFunction { t1: Any, t2: School ->
                    Pair(t1.toString(), t2.name)
                }
            ).filter { it.first != it.second }
            .map { it.first }
            .switchMapSingle {
                if (it.length >= 2) {
                    signUpInteractor.getSchoolList(it)
                } else {
                    Single.just(listOf())
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isEmpty()) {
                    view.setSchoolListVisible(false)
                } else {
                    view.setSchoolListVisible(true)
                }
                signUpInteractor.schoolId = null
                view.addSchoolList(it)
            }
            .bindUntilClear()
    }

    override fun subscribeIdol() {

        subscribeIdolChanges()
        view.idolSelect.onNext(IdolDto(-1, "", listOf(), listOf()))
        view.setIdolListVisible(false)
    }

    private fun subscribeIdolChanges() {
        Observable.merge(view.idolChanges, view.idolSelect)
            .doOnNext {
                if (it is IdolDto) {
                    view.setIdolListVisible(false)
                    view.setIdolText(it.name)
                    signUpInteractor.idolId = it.id.toLong()
                }
            }.filter { it is CharSequence }
            .withLatestFrom(
                view.idolSelect,
                BiFunction { t1: Any, t2: IdolDto ->
                    Pair(t1.toString(), t2.name)
                }
            ).filter { it.first != it.second }
            .map { it.first }
            .switchMapSingle {
                if (it.length >= 2) {
                    signUpInteractor.getIdolList(it)
                } else {
                    Single.just(listOf())
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isEmpty()) {
                    view.setIdolListVisible(false)
                } else {
                    view.setIdolListVisible(true)
                }
                signUpInteractor.idolId = null
                view.addIdolList(it)
            }
            .bindUntilClear()
    }

    override fun subscribeSignUp() {
        view.signUpClick
            .filter { signUpInteractor.hasSchoolIdAndIdolId() }
            .switchMapSingle {
                signUpInteractor.signUp(
                    view.email.toString(),
                    view.nickname.toString(),
                    view.password.toString(),
                    signUpInteractor.schoolId!!,
                    signUpInteractor.idolId!!
                )
            }.observeOn(AndroidSchedulers.mainThread())
            .retry { _, e ->
                Log.e("error", "error, message: ${e.message}")
                true
            }
            .doOnNext { signUpInteractor.saveToken(it) }
            .switchMap { signUpInteractor.savedTokenChanges() }
            .flatMapSingle {
                signUpInteractor.getMyInfo()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .retry { count, e ->
                Log.e("error", "count: $count, error, message: ${e.message}")
                view.showToast("다시 로그인해주세요")
                true
            }
            .subscribe{ view.showMainUI() }
            .bindUntilClear()
    }

    override fun subscribeBack() {
        view.backButtonsClick
            .subscribe {
                view.deleteUI()
            }.bindUntilClear()
    }
}