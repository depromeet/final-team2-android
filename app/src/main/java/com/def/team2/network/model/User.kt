package com.def.team2.network.model


data class User (
    val ballotList : List<Any>,
    val email:String,
    val id:Long,
    val idolIdList:List<Long>,
    val nickName:String,
    val schoolList:List<Long>,

    val restBallotsCount: Long?,
    val lastAttendantDate: String?
)