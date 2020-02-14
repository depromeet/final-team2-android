package com.def.team2.network.model

import com.google.gson.annotations.Expose

data class IdolGroup(
    val id: Long,
    val name: String,

    @Expose(serialize = true, deserialize = false)
    val fans: List<User>,
    @Expose(serialize = true, deserialize = false)
    val images: List<Image>,
    @Expose(serialize = true, deserialize = false)
    val members: List<Idol>,
    @Expose(serialize = true, deserialize = false)
    val users: Any,
    val ballots:List<Ballot>
)

data class IdolGroupResponse(
    val id: Long,
    val name: String,
    val ballets: List<Long>,
    val fans: List<Long>,
    val images: List<Long>,
    val members: List<Long>
) {
    data class Image(
        val id: Int,
        val url: String
    )
}
