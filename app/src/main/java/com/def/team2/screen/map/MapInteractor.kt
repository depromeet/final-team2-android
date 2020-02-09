package com.def.team2.screen.map

import android.content.Context
import com.def.team2.base.UserData
import com.def.team2.network.Api
import com.def.team2.network.model.IdolGroup
import com.def.team2.network.model.Location
import com.def.team2.network.model.School
import com.def.team2.util.idolKingdomApi
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import io.reactivex.Flowable

class MapInteractor(context: Context) {

    lateinit var location: Location
    var boundBox: LatLngBounds? = null

    private val schoolSet: MutableSet<School.Level> = mutableSetOf()

    private val idolKingdomApi: Api by lazy {
        context.idolKingdomApi
    }

    init {
        UserData.school?.let {
            location = it.location
        } ?: kotlin.run {
            location = Location(defaultLat, defaultLng)
        }
    }


    /*
    2020-02-10 00:52:26.882 24287-24287/com.def.team2 E/position check!: latitude: 37.502341, longitude: 127.04779400000002
    2020-02-10 00:52:26.883 24287-24287/com.def.team2 E/center check!: latitude: 37.53778847169898, longitude: 127.04779399998651
    2020-02-10 00:52:26.884 24287-24287/com.def.team2 E/nsew check!: south: 37.240812937285426, north: 37.834764006112536, west: 126.76647049225079 , east: 127.32911750772223
    */

    fun addSchoolLevel(level: School.Level) {
        schoolSet.add(level)
    }

    fun deleteSchoolLevel(level: School.Level) {
        schoolSet.remove(level)
    }

    fun hasSchoolLevel(level: School.Level): Boolean =
        schoolSet.contains(level)

    fun getMySchool(): School? =
        UserData.school

    fun getSchoolList(): Flowable<List<School>> {
//        boundBox?.let {
//            return idolKingdomApi
//                .getNearSchoolList(it.latSouth, it.latNorth, it.lonWest, it.lonEast, 100)
//                .onErrorResumeNext { Single.just(listOf()) }
//                .toFlowable()
//                .map {schoolList ->
//                    if (schoolSet.size == 0) {
//                        schoolList
//                    } else {
//                        schoolList.filter {school -> hasSchoolLevel(school.level) }
//                    }
//                }
//                .subscribeOn(Schedulers.io())
//
//        } ?: kotlin.run {
//            return Flowable.just(listOf())
//        }

        return Flowable.just(
            listOf(
                // FixMe 임시로 users 에 idol id 를 넣었다.
                School(7867, "거창고등학교", "서울 종로구 혜화동", Location(37.59156, 127.000565), School.Level.MIDDLE, 1, ""),
                School(7868, "거창공업고등학교", "서울 종로구 행촌동", Location(37.573273, 126.96191), School.Level.HIGH, 2, ""),
                School(8081, "거창나래학교", "서울 종로구 혜화동", Location(37.59156, 127.000565), School.Level.MIDDLE, 3, ""),
                School(-1, "배재고등학교", "서울 강동구 고덕동", Location(37.556092, 127.150819), School.Level.HIGH, 1, ""),
                School(-2, "한영외국어고등학교", "서울 강동구 상일동", Location(37.548102, 127.157241), School.Level.HIGH, 3, ""),
                School(-3, "한영고등학교", "서울 강동구 상일동", Location(37.549008, 127.158209), School.Level.HIGH, 2, ""),
                School(-4, "광문고등학교", "서울 강동구 고덕동", Location(37.560283, 127.158255), School.Level.HIGH, 2, ""),
                School(-5, "명일여자고등학교", "서울 강동구 명일동", Location(37.549904, 127.150277), School.Level.HIGH, 3, ""),
                School(-6, "한영중학교", "서울 강동구 상일동", Location(37.549144, 127.157335), School.Level.MIDDLE, 1, ""),
                School(-7, "배재중학교", "서울 강동구 고덕동", Location(37.555641, 127.149619), School.Level.MIDDLE, 1, ""),
                School(-7, "명일중학교", "서울 강동구 고덕동", Location(37.557242, 127.148051), School.Level.MIDDLE, 1, "")
            )
        ).map {
            if (schoolSet.size == 0) {
                it
            } else {
                it.filter {school -> hasSchoolLevel(school.level) }
            }
        }
    }

    fun getIdolRankInSchool(): Flowable<List<IdolGroup>> {
        return Flowable.just(
            mutableListOf()
        )
    }

    companion object {
        const val defaultLat = 37.571235
        const val defaultLng = 126.976504
    }
}