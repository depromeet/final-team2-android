package com.def.team2.base

import com.def.team2.network.model.*

object UserData {

    var user: User? = User(listOf("","","",""),"email@email.com",-2, listOf(),"선념규", listOf())

    // FixMe 테스트용 임시 데이터니 제거(null 로 변경) 필요
    var school: School? = School(-1, "배재고등학교", "서울 강동구 고덕동", Location(37.556092, 127.150819), School.Level.HIGH, "", "")

    val idolList: MutableList<IdolGroup> = mutableListOf()
    var currentVote : VoteResponseDto? = null
}