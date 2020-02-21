package com.def.team2.network.model


data class User(
        val ballotList: List<Any>,
        val email: String,
        val id: Long,
        val idols: List<Long>,
        val nickName: String,
        val schools: List<Long>,
        val restBallotsCount: Long = 0,
        val profileImage: String? = ""
)