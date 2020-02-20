package com.def.team2.network.model

import com.google.gson.annotations.Expose

data class School(
    val id: Long,
    val name: String,
    val address: String,
    val location: Location,
    val level: Level,
    val markerImage: String?,

    @Expose(serialize = true, deserialize = false)
    val users: Any,
    @Expose(serialize = true, deserialize = false)
    val ballots: Any

) {
    enum class Level {
        ELEMENT, MIDDLE, HIGH, UNIVERSAL
    }
}