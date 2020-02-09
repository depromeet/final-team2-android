package com.def.team2.screen.map

import com.def.team2.network.model.Location
import com.def.team2.network.model.School
import com.mapbox.mapboxsdk.geometry.LatLngBounds
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
        interactor.getSchoolList()
            .subscribe {
                view.showSchoolList(it)
            }
            .bindUntilClear()
    }

    override fun updateMapPosition(cameraLat: Double, cameraLng: Double, cameraBounds: LatLngBounds, refreshing: Boolean) {
        interactor.location = Location(cameraLat, cameraLng)
        interactor.boundBox = cameraBounds
        if (refreshing) {
            loadSchoolList()
        }
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

    override fun loadMySchool() {
        val schoolPosition: Location = interactor.getMySchool()?.location ?: kotlin.run {
            Location(MapInteractor.defaultLat, MapInteractor.defaultLng)
        }

        view.moveMapPosition(schoolPosition.latitude, schoolPosition.longitude)
        view.hideMapOption()
    }

    override fun loadMyLocation() {
        if (interactor.isAccessMyLocation()) {
            interactor.getMyLocation()?.let {
                view.moveMapPosition(it.latitude, it.longitude)
                view.hideMapOption()
            } ?: kotlin.run {
                view.showToast("내 위치를 가져오는데 실패했습니다.")
            }
        } else {
            view.showLocationPermissionUI()
        }
    }

    override fun loadIdolRankInSchool(school: School) {
        view.showSchoolIdolRank(school, listOf())
    }

    override fun removeIdolRankInSchool() {
        view.hideSchoolIdolRank()
    }
}