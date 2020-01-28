package com.def.team2.util

import android.util.Log


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