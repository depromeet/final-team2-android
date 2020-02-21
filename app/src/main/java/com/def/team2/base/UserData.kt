package com.def.team2.base

import com.def.team2.network.model.IdolGroup
import com.def.team2.network.model.School
import com.def.team2.network.model.User

object UserData {

    var user: User? = null

    // FixMe 테스트용 임시 데이터니 제거(null 로 변경) 필요
    var school: School? = null

    val idolList: MutableList<IdolGroup> = mutableListOf()
    var currentVote : VoteResponseDto? = null
    var currentMyVote : VoteResponseDto? = null
}