package com.def.team2.util

import android.content.Context
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.def.team2.network.Api
import com.def.team2.network.RetrofitProvider
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Toast R.String.~~로 접근
 */
fun Context.toast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resId, duration).show()
}

/**
 * Toast String 으로 접근
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

/**
 * Email check
 */
fun CharSequence.isEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * ViewClick 구현시 300ms 내에 중복클릭은 제외
 */
fun View.throttleClicks() = this.clicks().throttleFirst(300, java.util.concurrent.TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())!!

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = true): View {
    return LayoutInflater.from(context).inflate(resource, this, attachToRoot)!!
}

/**
 * sharedPreferences 구현
 */
fun Context.sharedPreferences() = this.getSharedPreferences("base", Context.MODE_PRIVATE)!! // TODO KEy

val Context.coupleLinkApi: Api
    get() = RetrofitProvider(this).coupleLinkApi