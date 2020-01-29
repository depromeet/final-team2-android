package com.def.team2.screen.map

import android.content.Context
import com.def.team2.network.Api
import com.def.team2.network.model.School
import com.def.team2.util.idolKingdomApi

class MapInteractor(context: Context) {

    private val schoolSet: MutableSet<School.Level> = mutableSetOf()

    private val idolKingdomApi: Api by lazy {
        context.idolKingdomApi
    }

    fun addSchoolLevel(level: School.Level) {
        schoolSet.add(level)
    }

    fun deleteSchoolLevel(level: School.Level) {
        schoolSet.remove(level)
    }

    fun hasSchoolLevel(level: School.Level): Boolean {
        return schoolSet.contains(level)
    }
}