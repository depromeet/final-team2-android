package com.def.team2.network.model


data class User (
    val ballotList : List<Any>,
    val email:String,
    val id:Int,
    val idolIdList:List<IdolGroup>,
    val nickName:String,
    val schoolList:List<School>
)