package com.cexmobility.corekotlin.utils.extensions


import android.content.Context
import android.text.format.DateUtils
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun Long.convertLongToTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(date)
}