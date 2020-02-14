package com.def.team2.screen.main

import com.def.team2.util.getTimeRemaining
import com.def.team2.util.numberFormatZero
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun start() {
        subscribeClick()
        view.changeType(HomeContract.View.Type.MAP)
    }

    override fun subscribeClick() {
        view.rankClick.subscribe {
            view.changeType(HomeContract.View.Type.RANK)
        }.bindUntilClear()
        view.mapClick.subscribe {
            view.changeType(HomeContract.View.Type.MAP)
        }.bindUntilClear()
        view.searchClcik.subscribe {
            view.shpwSearchDialog()
        }.bindUntilClear()
    }

    override fun subscribeTime() {
        Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            view.updateDate(formatTimeRemaining(getTimeRemaining("")))
        }.bindUntilClear()
    }

    private fun formatTimeRemaining(date: Long): String {
        val sec = 60
        val min = 60 * 60

        val hourRemainder = date % min
        val hourRemaining = date / min
        val minRemaining = hourRemainder / sec
        val secRemaining = hourRemainder % sec

        return "${if (hourRemaining < 0) "00" else hourRemaining.toString().numberFormatZero()} : ${if (minRemaining < 0) "00" else minRemaining.toString().numberFormatZero()} : ${if (secRemaining < 0) "00" else secRemaining.toString().numberFormatZero()}"
    }
}