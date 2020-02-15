package com.def.team2.util

import android.util.Log
import java.text.SimpleDateFormat


fun d(msg: Any) {
    val fullClassName = Thread.currentThread().stackTrace[3].className
    val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
    Log.d("IdolKingdom", "[$className.$methodName():$lineNumber] : $msg")
}

fun e(msg: Any) {
    val fullClassName = Thread.currentThread().stackTrace[3].className
    val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
    Log.e("IdolKingdom", "[$className.$methodName():$lineNumber] : $msg")
}

fun getTimeRemaining(endDate:String): Long {
    val endDateSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    return endDateSimpleDateFormat.parse(endDate).time - System.currentTimeMillis()
}

fun formatTimeRemaining(date: Long): String {
    val sec = 60
    val min = 60 * 60

    val hourRemainder = date % min
    val hourRemaining = date / min
    val minRemaining = hourRemainder / sec
    val secRemaining = hourRemainder % sec

    return "${if (hourRemaining < 0) "00" else hourRemaining.toString().numberFormatZero()} : ${if (minRemaining < 0) "00" else minRemaining.toString().numberFormatZero()} : ${if (secRemaining < 0) "00" else secRemaining.toString().numberFormatZero()}"
}