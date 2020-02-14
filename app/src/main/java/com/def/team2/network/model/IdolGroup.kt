package com.def.team2.network.model

import com.google.gson.annotations.Expose

data class IdolGroup(
    val id: Long,
    val name: String,

    @Expose(serialize = true, deserialize = false)
    val fans: Any,
    @Expose(serialize = true, deserialize = false)
    val images: Any,
    @Expose(serialize = true, deserialize = false)
    val members: Any,
    @Expose(serialize = true, deserialize = false)
    val users: Any
)

data class IdolGroupResponse(
    val id: Long,
    val name: String,
    val ballets: List<Long>,
    val fans: List<Long>,
    val images: List<Long>,
    val members: List<Long>
)