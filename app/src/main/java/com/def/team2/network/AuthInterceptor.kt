package com.def.team2.network

import android.content.Context
import com.def.team2.util.KEY_TOKEN
import com.def.team2.util.sharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {

        val token = context.sharedPreferences()
            .getString(KEY_TOKEN, null)

        token?.let {
            val newRequest = request().newBuilder().run {
                addHeader("Authorization", "Bearer $token")
                build()
            }
            proceed(newRequest)
        } ?: run {
            proceed(request())
        }
    }
}