package com.def.team2.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface BasePresenter {

    fun start()

}

interface BaseRxPresenter: BasePresenter {

    val disposables: CompositeDisposable

    fun Disposable.bindUntilClear() {
        if (!disposables.isDisposed) {
            disposables.add(this)
        } else {
            if (!isDisposed) {
                dispose()
            }
        }
    }

    fun clearDisposable() {
        disposables.clear()
    }
}