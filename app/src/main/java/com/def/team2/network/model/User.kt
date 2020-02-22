package com.def.team2.network.model


data class User(
        val ballotList: MutableList<Long>,
        val email: String,
        val id: Long,
        val idols: MutableList<Long>,
        val nickName: String,
        val schools: MutableList<Long>,
        val lastAttendantDate: String?,
        val restBallotsCount: Long = 0,
        val profileImage: String? = ""
)