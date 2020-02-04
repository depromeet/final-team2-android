package com.def.team2.screen.search

import com.def.team2.util.e
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class SearchPresenter(private val view: SearchContract.View) : SearchContract.Presenter {


    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
    }

    override fun subscribeSearch() {
        val search = "B"
        Single.zip(listOf(view.getApiProvider().searchIdolList(search), view.getApiProvider().searchSchoolList(search)))
        {
            e(it)
        }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    e(it)
                }, {
                    e(it)
                })
                .bindUntilClear()
    }
}