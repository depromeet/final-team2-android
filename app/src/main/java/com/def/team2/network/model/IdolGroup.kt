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