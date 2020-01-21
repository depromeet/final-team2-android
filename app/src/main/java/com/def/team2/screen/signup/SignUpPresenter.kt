package com.def.team2.screen.signup

import android.util.Log
import com.def.team2.network.RetrofitProvider
import com.def.team2.network.model.SignUpRequest
import com.def.team2.util.isEmail
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class SignUpPresenter(
    val view: SignUpContract.View
) : SignUpContract.Presenter {

    private var schoolId: Int? = null
    private var idolId: Int? = null

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
            .map {
                view.nickname
            }.filter {
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
            .map {
                view.email
            }.filter {
                if (it.isEmail()) {
                    return@filter true
                }
                view.showToast("Invalid Email")
                return@filter false
            }
            .observeOn(Schedulers.io())
            .switchMapCompletable {
                view.getApiProvider()
                    .checkEmailDuplicated(mapOf(Pair("email", it.toString())))
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { view.showPasswordUI() }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .retry { _, e ->
                Log.e("error", "error, message: ${e.message}")
                if (e is HttpException) {
                    if (e.code() == 403) {
                        view.showToast("Email is Duplicated")
                    } else {
                        view.showToast("Unknown Error")
                    }
                }
                true
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.e("complete", "check complete!!!!")
            }.bindUntilClear()
    }

    override fun subscribePassword() {
        view.passwordNextClick
            .map {
                view.password
            }.filter {
                if (it.isNotEmpty()) {
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

        subscribeSchoolSelect()
        subscribeSchoolChanges()
        view.schoolSelect.onNext(Pair(0, ""))

        view.schoolNextClick
            .map {
                view.school
            }.filter {
                if (it.isNotEmpty()) {
                    return@filter true
                }
                view.showToast("Invalid School")
                return@filter false
            }
            .subscribe {
                view.showMyIdolUI()
            }.bindUntilClear()

    }

    private fun subscribeSchoolSelect() {
        view.schoolSelect
            .subscribe{
                view.setSchoolListVisible(false)
                view.setSchoolText(it.second)
            }.bindUntilClear()
    }

    private fun subscribeSchoolChanges() {

        view.schoolChanges
            .debounce(1000, TimeUnit.MILLISECONDS)
            .withLatestFrom(
                view.schoolSelect,
                BiFunction { t1: CharSequence, t2: Pair<Int, String> ->
                    Pair(t1.toString(), t2.second)
                }
            )
            .filter {
                it.first != it.second
            }.map {
                it.first
            }.filter {
                it.length >= 2
            }.observeOn(Schedulers.io())
            .switchMapSingle {
                view.getApiProvider()
                    .searchSchoolList(it)
                    .doOnError {  }
            }

            // FixMe 테스트 시 아래를 사용
//            .switchMap {
//                when (it) {
//                    "풍동" -> return@switchMap Observable.just(listOf(Pair(1, "풍동"), "풍동고등학교"))
//                    "abcd" -> return@switchMap Observable.just(listOf("풍동", "풍동고등학교", "풍동중학교", "풍동 뭐시기"))
//                    "defg" -> return@switchMap Observable.just(listOf("대치", "대치고등학교", "대치중학교", "대치대치", "음...대치?"))
//                    "qwer" -> return@switchMap Observable.just(listOf("강남", "강남고등학교", "강남중학교", "강남해커스", "강남대성"))
//                    else -> return@switchMap Observable.just(listOf<String>())
//                }
//            }.map { schoolList ->
//                schoolList.map {
//                    Pair(1, it)
//                }
//            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it.isEmpty()) {
                    view.setSchoolListVisible(false)
                } else {
                    view.setSchoolListVisible(true)
                }
            }
            .doOnError {
                // 에러 발생 처리
            }
            .subscribe {
                Log.e("asdg", "asdf")
//                view.addSchoolList(it)
            }
            .bindUntilClear()
    }

    override fun subscribeIdol() {

        subscribeIdolSelect()
        subscribeIdolChanges()
        view.idolSelect.onNext(Pair(0, ""))
    }

    private fun subscribeIdolSelect() {
        view.idolSelect
            .subscribe{
                view.setIdolListVisible(false)
                view.setIdolText(it.toString())
            }.bindUntilClear()
    }

    private fun subscribeIdolChanges() {
        view.idolChanges
            .withLatestFrom(
                view.idolSelect,
                BiFunction { t1: CharSequence, t2: Pair<Int, String> ->
                    Pair(t1, t2.toString())
                }
            )
            .filter {
                it.first != it.second
            }
            .map {
                it.first
            }.filter {
                it.length >= 2
            }.observeOn(Schedulers.io())
            .flatMap {
                // Todo api 요청

                // FixMe 더미 데이터 제거
                when (it) {
                    "abcd" -> return@flatMap Observable.just(listOf("풍동", "풍동고등학교", "풍동중학교", "풍동 뭐시기"))
                    "defg" -> return@flatMap Observable.just(listOf("대치", "대치고등학교", "대치중학교", "대치대치", "음...대치?"))
                    "qwer" -> return@flatMap Observable.just(listOf("강남", "강남고등학교", "강남중학교", "강남해커스", "강남대성"))
                    else -> return@flatMap Observable.just(listOf<String>())
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                if (it.isEmpty()) {
                    view.setIdolListVisible(false)
                } else {
                    view.setIdolListVisible(true)
                }
            }
            .doOnError {
                // 에러 발생 처리
            }
            .subscribe {
//                view.addIdolList(it)
            }
            .bindUntilClear()
    }

    override fun subscribeSignUp() {
        view.signUpClick
            .filter {
                schoolId != null && idolId != null
            }
            .map {
                SignUpRequest(
                    view.email.toString(),
                    view.nickname.toString(),
                    view.password.toString(),
                    schoolId!!,
                    idolId!!
                )
            }.subscribe {

            }.bindUntilClear()
    }

    override fun subscribeBack() {
        view.backButtonsClick
            .subscribe {
                view.deleteUI()
            }.bindUntilClear()
    }
}