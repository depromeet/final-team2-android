package com.def.team2.screen.signin

import android.content.Context
import com.def.team2.util.sharedPreferences

class SaveToken(private val context: Context) {

    operator fun invoke(token: String) {
        context.sharedPreferences()
            .edit()
            .putString("token", token)
            .apply()
    }
}