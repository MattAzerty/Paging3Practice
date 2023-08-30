package com.withings.mycomposeandblepractice.utils

import android.util.Log

fun String.printLog(tag: String = "CustomTag") {
    Log.e(tag, this)
}