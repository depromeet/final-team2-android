package com.def.team2.screen.profile.setting

import com.def.team2.util.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ProfileSettingPresenter(private val view: ProfileSettingContract.View) :
    ProfileSettingContract.Presenter {


    override val disposables: CompositeDisposable = CompositeDisposable()
    override fun start() {
        subscribeSetting()
        subscribeEdit()
    }

    override fun subscribeSetting() {
        view.setSetting() // TODO setting
    }

    override fun subscribeEdit() {
        view.editClick()
                .doOnError { e("editClick Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  }
                .bindUntilClear()

    }
}