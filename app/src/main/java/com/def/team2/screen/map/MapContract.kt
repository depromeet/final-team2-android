package com.def.team2.screen.map

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView

interface MapContract {

    interface View: BaseRxView<Presenter> {

    }

    interface Presenter: BaseRxPresenter {

    }
}