package com.def.team2.screen.map

import com.def.team2.network.model.School
import io.reactivex.disposables.CompositeDisposable

class MapPresenter(
    private val view: MapContract.View,
    private val interactor: MapInteractor
): MapContract.Presenter {

    override fun start() {

    }

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun openFilterView() {
        view.setSchoolFilterUI(true)
        view.hideMapOption()
    }

    override fun loadSchoolList() {
        view.setSchoolFilterUI(false)

        // Todo: Api 호출
    }

    override fun changeSchoolLevel(schoolLevel: School.Level) {
        if (interactor.hasSchoolLevel(schoolLevel))
            deleteSchoolLevel(schoolLevel, false)
        else
            addSchoolLevel(schoolLevel, false)
    }

    override fun addSchoolLevel(schoolLevel: School.Level, refreshing: Boolean) {
        interactor.addSchoolLevel(schoolLevel)
        view.setFilterOption(schoolLevel, true)
        if (refreshing) {
            loadSchoolList()
        }
    }

    override fun deleteSchoolLevel(schoolLevel: School.Level, refreshing: Boolean) {
        interactor.deleteSchoolLevel(schoolLevel)
        view.setFilterOption(schoolLevel, false)
        if (refreshing) {
           loadSchoolList()
        }
    }
}