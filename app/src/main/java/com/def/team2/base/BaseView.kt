package com.def.team2.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

interface BaseView<T> {

    var presenter: T

}

interface BaseRxView<T: BaseRxPresenter>: BaseView<T>, LifecycleObserver {

    var lifeCycleOwner: LifecycleOwner

    fun setLifecycle() {
        lifeCycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun clearSubscribeOnDestroy() {
        presenter.clearDisposable()
        lifeCycleOwner.lifecycle.removeObserver(this)
    }
}