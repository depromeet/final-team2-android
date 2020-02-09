package com.def.team2.screen.search

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class SearchPresenter(private val view: SearchContract.View) : SearchContract.Presenter {


    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
    }

    override fun subscribeSearch() {
        //TODO 두개 합쳐서 search에 넣어줌
        val search = ""
        Single.merge(view.getApiProvider().searchIdolList(search), view.getApiProvider().searchSchoolList(search, 5))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }
                .bindUntilClear()
    }
}