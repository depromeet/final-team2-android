package com.def.team2.screen.profile.setting

import com.def.team2.base.UserData
import com.def.team2.screen.search.SearchFragment
import com.def.team2.screen.search.SearchPresenter
import com.def.team2.util.e
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ProfileSettingPresenter(private val view: ProfileSettingContract.View) :
    ProfileSettingContract.Presenter {


    override val disposables: CompositeDisposable = CompositeDisposable()
    override fun start() {
        subscribeSetting()
        subscribeEdit()
        subscribeSchool()
    }

    override fun subscribeSetting() {
        UserData.user?.apply {
            view.setSetting(this)
        }
    }

    override fun subscribeEdit() {
        view.editClick
                .doOnError { e("editClick Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  }
                .bindUntilClear()

    }
    override fun subscribeSchool() {
        view.schoolClick
                .doOnError { e("schoolClick Error") }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.showSearchDialog(SearchFragment(SearchPresenter.Type.ALL))
                }
                .bindUntilClear()
    }
}