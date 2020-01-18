package com.def.team2

import android.content.Context
import com.def.team2.util.KEY_TOKEN
import com.def.team2.util.sharedPreferences

class SaveToken(private val context: Context) {

    operator fun invoke(token: String) {
        context.sharedPreferences()
            .edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }
}