package com.def.team2.screen.map

import com.def.team2.base.BaseRxPresenter
import com.def.team2.base.BaseRxView
import com.def.team2.network.model.IdolGroup
import com.def.team2.network.model.School

interface MapContract {

    interface View: BaseRxView<Presenter> {

        val isActive: Boolean

        fun showSchoolList(schoolList: List<School>)

        fun hideMapOption()

//        fun showSearchUI()

        fun setSchoolFilterUI(active: Boolean)

        fun setFilterOption(filterType: School.Level, active: Boolean)

        fun moveMapPosition(lat: Double, lng: Double)

        fun showSchoolIdolRank(school: School, idolGroupList: List<IdolGroup>)

        fun hideSchoolIdolRank()

        fun showTotalIdolRank()
    }

    interface Presenter: BaseRxPresenter {

        fun openFilterView()

        fun loadSchoolList()

        fun changeSchoolLevel(schoolLevel: School.Level)

        fun addSchoolLevel(schoolLevel: School.Level, refreshing: Boolean)

        fun deleteSchoolLevel(schoolLevel: School.Level, refreshing: Boolean)

        fun loadMySchool()

        fun loadMyLocation()

        fun loadIdolRankInSchool(school: School)

        fun removeIdolRankInSchool()
    }
}