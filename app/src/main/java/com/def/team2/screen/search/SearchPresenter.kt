package com.def.team2.screen.search

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SearchPresenter(private val view: SearchContract.View, private val type: Type) : SearchContract.Presenter {

    enum class Type {
        ALL, IDOL, SCHOOL
    }

    private var currentType = Type.ALL

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        currentType = type
        subscribeSearch()
        subscribeClose()
    }

    override fun subscribeSearch() {
        val search = "B"
        Single.zip(listOf(view.getApiProvider().searchIdolList(search), view.getApiProvider().searchSchoolList(search, 5)))
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
        view.schoolChanges.throttleLast(300, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            val search = it.toString()
            if (search.isEmpty()) {
                view.adapterClear()
                return@subscribe
            }
            val searchList = when (currentType) {
                Type.ALL -> listOf(view.getApiProvider().searchIdolList(search), view.getApiProvider().searchSchoolList(search))
                Type.IDOL -> listOf(view.getApiProvider().searchIdolList(search))
                Type.SCHOOL -> listOf(view.getApiProvider().searchSchoolList(search))
            }
            Single.zip(searchList) { array ->
                if (array.size == 1) array[0] as List<Any>
                else {
                    val mergeList = mutableListOf<Any>()
                    array.forEach { data -> mergeList.addAll(data as List<Any>) }
                    mergeList
                }
            }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view.setSearchResponse(it.toList())
                    }, {
                        it.printStackTrace()
                    })
                    .bindUntilClear()
        }.bindUntilClear()
    }
    override fun subscribeClose() {
        view.closeClick.subscribe {
            view.dismissDialog()
        }.bindUntilClear()
    }
}