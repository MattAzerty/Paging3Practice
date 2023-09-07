package com.withings.mycomposeandblepractice.utils

import android.util.Log
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken

fun String.printLog(tag: String = "CustomTag") {
    Log.e(tag, this)
}

// Returns the normalized centered item on screen offset (-1,1)
fun LazyListLayoutInfo.normalizedItemPosition(key: Any): Float =
    visibleItemsInfo
        .firstOrNull { it.key == key }
        ?.let {
            val center = (viewportEndOffset + viewportStartOffset - it.size) / 2F
            (it.offset.toFloat() - center) / center
        }
        ?: 0F
//Json adapter
inline fun <reified T> Gson.fromJson(json: String): T? = try {
    fromJson<T>(json, object : TypeToken<T>() {}.type)
} catch (e: JsonParseException) {
    null
}