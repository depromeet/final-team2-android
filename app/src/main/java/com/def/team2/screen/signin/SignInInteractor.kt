package com.def.team2.screen.signin

import android.content.Context
import android.content.SharedPreferences
import com.def.team2.base.UserData
import com.def.team2.network.Api
import com.def.team2.network.model.SignInRequest
import com.def.team2.util.KEY_TOKEN
import com.def.team2.util.idolKingdomApi
import com.def.team2.util.sharedPreferences
import com.f2prateek.rx.preferences2.RxSharedPreferences
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class SignInInteractor(context: Context) {

    private val idolKingdomApi: Api by lazy {
        context.idolKingdomApi
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.sharedPreferences()
    }

    fun signIn(email: String, password: String): Single<String> =
        idolKingdomApi
            .signIn(SignInRequest(email, password))
            .map { it.token }
            .subscribeOn(Schedulers.io())

    fun getMyInfo() =
        idolKingdomApi
            .getMe()
            .map { UserData.user = it }
            .subscribeOn(Schedulers.io())

    fun saveToken(token: String) {
        sharedPreferences
            .edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    fun savedTokenChanges(): Observable<String> =
        RxSharedPreferences.create(sharedPreferences)
            .getString(KEY_TOKEN).asObservable()
            .filter { it.isNotEmpty() }
}