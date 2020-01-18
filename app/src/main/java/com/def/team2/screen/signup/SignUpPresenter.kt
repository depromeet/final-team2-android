package com.def.team2.screen.signup

import com.def.team2.util.isEmail
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class SignUpPresenter(
    val view: SignUpContract.View
) : SignUpContract.Presenter {

    override fun start() {
        subscribeNickName()
        subscribeEmail()
        subscribePassword()
        subscribeSchool()
        subscribeIdol()
        subscribeBack()
    }

    override val disposables: CompositeDisposable = CompositeDisposable()

    override val school: PublishSubject<CharSequence> = PublishSubject.create()

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
            .subscribe {
                view.showPasswordUI()
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

        val schoolSubject = school.share()

        schoolSubject
            .subscribe{
                view.setSchoolListVisible(false)
                view.setSchoolText(it.toString())
            }.bindUntilClear()

        view.schoolChanges
            .withLatestFrom(
                schoolSubject,
                BiFunction { t1: CharSequence, t2: CharSequence ->
                    Pair(t1.toString(), t2.toString())
                }
            )
            .filter {
                it.first != it.second
            }.map {
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
                    view.setSchoolListVisible(false)
                } else {
                    view.setSchoolListVisible(true)
                }
            }
            .doOnError {
                // 에러 발생 처리
            }
            .subscribe {
                view.addSchoolList(it)
            }
            .bindUntilClear()

        school.onNext("")

    }

    override fun subscribeIdol() {

    }

    override fun subscribeBack() {
        view.backButtonsClick
            .subscribe {
                view.deleteUI()
            }.bindUntilClear()
    }
}