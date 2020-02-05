package com.def.team2.screen.search

import com.def.team2.util.e
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SearchPresenter(private val view: SearchContract.View, private val type: Type = Type.ALL) : SearchContract.Presenter {

    enum class Type {
        ALL, IDOL, SCHOOL
    }

    private var currentType = Type.ALL

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        currentType = type
    }

    override fun subscribeSearch(search: String) {
        view.schoolChanges.throttleLast(300, TimeUnit.MILLISECONDS).subscribe {
            val searchList = when (currentType) {
                Type.ALL -> listOf(view.getApiProvider().searchIdolList(search), view.getApiProvider().searchSchoolList(search))
                Type.IDOL -> listOf(view.getApiProvider().searchIdolList(search))
                Type.SCHOOL -> listOf(view.getApiProvider().searchSchoolList(search))
            }
            Single.zip(searchList) { it }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view.setSearchResponse(it.toList())
                    }, {
                        e(it)
                    })
                    .bindUntilClear()
        }.bindUntilClear()
    }

    fun setSearchType(type: Type) {
        currentType = type
    }

}