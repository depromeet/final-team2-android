package com.def.team2.screen.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.os.Build
import com.def.team2.base.UserData
import com.def.team2.network.Api
import com.def.team2.network.model.Location
import com.def.team2.network.model.School
import com.def.team2.screen.map.model.RankIdol
import com.def.team2.util.idolKingdomApi
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MapInteractor(private val context: Context) {

    lateinit var location: Location
    var boundBox: LatLngBounds? = null

    private val schoolSet: MutableSet<School.Level> = mutableSetOf()

    private val idolKingdomApi: Api by lazy {
        context.idolKingdomApi
    }

    private val locationManager: LocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    init {
        UserData.school?.let {
            location = it.location
        } ?: kotlin.run {
            location = Location(defaultLat, defaultLng)
        }
    }

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
        boundBox?.let {
            return idolKingdomApi
                .getNearSchoolList(it.latSouth, it.latNorth, it.lonWest, it.lonEast, 10)
                .onErrorResumeNext { Single.just(listOf()) }
                .toFlowable()
                .map {schoolList ->
                    if (schoolSet.size == 0) {
                        schoolList
                    } else {
                        schoolList.filter {school -> hasSchoolLevel(school.level) }
                    }
                }
                .subscribeOn(Schedulers.io())

        } ?: kotlin.run {
            return Flowable.just(listOf())
        }

//        return Flowable.just(
//            listOf(
//                // FixMe 임시로 users 에 idol id 를 넣었다.
//                School(7867, "거창고등학교", "서울 종로구 혜화동", Location(37.59156, 127.000565), School.Level.MIDDLE, 1, ""),
//                School(7868, "거창공업고등학교", "서울 종로구 행촌동", Location(37.573273, 126.96191), School.Level.HIGH, 2, ""),
//                School(8081, "거창나래학교", "서울 종로구 혜화동", Location(37.59156, 127.000565), School.Level.MIDDLE, 3, ""),
//                School(-1, "배재고등학교", "서울 강동구 고덕동", Location(37.556092, 127.150819), School.Level.HIGH, 1, ""),
//                School(-2, "한영외국어고등학교", "서울 강동구 상일동", Location(37.548102, 127.157241), School.Level.HIGH, 3, ""),
//                School(-3, "한영고등학교", "서울 강동구 상일동", Location(37.549008, 127.158209), School.Level.HIGH, 2, ""),
//                School(-4, "광문고등학교", "서울 강동구 고덕동", Location(37.560283, 127.158255), School.Level.HIGH, 2, ""),
//                School(-5, "명일여자고등학교", "서울 강동구 명일동", Location(37.549904, 127.150277), School.Level.HIGH, 3, ""),
//                School(-6, "한영중학교", "서울 강동구 상일동", Location(37.549144, 127.157335), School.Level.MIDDLE, 1, ""),
//                School(-7, "배재중학교", "서울 강동구 고덕동", Location(37.555641, 127.149619), School.Level.MIDDLE, 1, ""),
//                School(-7, "명일중학교", "서울 강동구 고덕동", Location(37.557242, 127.148051), School.Level.MIDDLE, 1, "")
//            )
//        ).map {
//            if (schoolSet.size == 0) {
//                it
//            } else {
//                it.filter {school -> hasSchoolLevel(school.level) }
//            }
//        }
    }

    fun getIdolRankInSchool(school: School): Flowable<List<RankIdol>> {
        return idolKingdomApi.getSchoolRanking(school.id)
            .map { it.idols }
            .map { idolGroups -> idolGroups.sortedByDescending { it.currentBallots.size }.take(3) }
            .map { idols ->
                idols.map {
                    //Todo 이미지 없는 경우디폴트 이미지로 변경해야 함
                    val imgUrl = if (it.images.size >= 3) it.images[2] else ""
                    RankIdol(
                        school.id,
                        1,
                        it.name,
                        imgUrl,
                        school.name,
                        school.address
                    )
                }
            }
            .toFlowable()
            .subscribeOn(Schedulers.io())

//        return Flowable.just(
//            listOf(
//                RankIdol(
//                    school.id,
//                    1,
//                    "방탄소년단",
//                    "https://img.sbs.co.kr/newsnet/etv/upload/2019/04/17/30000626415_700.jpg",
//                    school.name,
//                    school.address
//                ),
//                RankIdol(
//                    school.id,
//                    3,
//                    "레드벨벳",
//                    "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/BBLqut9.img?h=0&w=720&m=6&q=60&u=t&o=f&l=f&x=265&y=329",
//                    school.name,
//                    school.address
//                ),
//                RankIdol(
//                    school.id,
//                    2,
//                    "아이유",
//                    "https://pds.joins.com/news/component/htmlphoto_mmdata/201910/21/htm_2019102182027833808.jpg",
//                    school.name,
//                    school.address
//                )
//            )
//        )
    }

    fun isAccessMyLocation(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }


    @SuppressLint("MissingPermission")
    fun getMyLocation(): Location? =
        locationManager.getLastKnownLocation(locationManager.getBestProvider(Criteria(), false))?.let {
            Location(it.latitude, it.longitude)
        }


    companion object {
        const val defaultLat = 37.571235
        const val defaultLng = 126.976504
    }
}


/*
2020-02-10 00:52:26.882 24287-24287/com.def.team2 E/position check!: latitude: 37.502341, longitude: 127.04779400000002
2020-02-10 00:52:26.883 24287-24287/com.def.team2 E/center check!: latitude: 37.53778847169898, longitude: 127.04779399998651
2020-02-10 00:52:26.884 24287-24287/com.def.team2 E/nsew check!: south: 37.240812937285426, north: 37.834764006112536, west: 126.76647049225079 , east: 127.32911750772223
*/