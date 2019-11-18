package com.cexmobility.corekotlin.data.general

import android.util.ArrayMap

import java.util.concurrent.TimeUnit

class RateLimiter<KEY>(timeOut: Int, timeUnit: TimeUnit) {

    private val timeOut: Long = timeUnit.toMillis(timeOut.toLong())
    private val timeStamps = ArrayMap<KEY, Long>()

    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        val lastFetched = timeStamps[key]
        val now = now()
        return when {
            lastFetched == null -> {
                timeStamps[key] = now
                true
            }
            now - lastFetched > timeOut -> {
                timeStamps[key] = now
                true
            }
            else -> false
        }

    }

    private fun now(): Long {
        return System.currentTimeMillis()
    }

    @Synchronized
    fun reset(key: KEY) {
        timeStamps.remove(key)
    }
}
