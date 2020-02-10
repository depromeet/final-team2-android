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
    val ballots:List<Ballot>
){
    data class Image(
        val id: Int,
        val url: String
    )
}