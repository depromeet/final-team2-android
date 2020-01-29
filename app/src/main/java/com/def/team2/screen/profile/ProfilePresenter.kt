package com.def.team2.screen.profile

import com.def.team2.util.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ProfilePresenter(private val view: ProfileContract.View) : ProfileContract.Presenter {

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        subscribeMoveFragment()
        subscribeEditImage()
    }

    override fun subscribeMoveFragment() {
        view.changeFragment(ProfileContract.View.Status.SCHOOL)
        view.clickSchool()
                .doOnError { e("clickSchool Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.changeFragment(ProfileContract.View.Status.SCHOOL) }
                .bindUntilClear()
        view.clickIdol()
                .doOnError { e("clickIdol Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.changeFragment(ProfileContract.View.Status.IDOL) }
                .bindUntilClear()
        view.clickSetting()
                .doOnError { e("clickSetting Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.changeFragment(ProfileContract.View.Status.SETTING) }
                .bindUntilClear()
    }

    override fun subscribeEditImage() {
        view.clickImageEdit()
                .doOnError { e("clickImageEdit Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  }
                .bindUntilClear()
    }


}