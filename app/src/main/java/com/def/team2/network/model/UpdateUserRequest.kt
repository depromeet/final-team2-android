package com.def.team2.network.model

data class UpdateUserRequest(
        val data: Data
) {
    data class Data(
            val email: String? = null,
            val password: String? = null,
            val nickName: String? = null,
            val schools: List<Long>? = null,
            val idols: List<Long>? = null
    )
}