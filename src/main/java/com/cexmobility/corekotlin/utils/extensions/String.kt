package com.cexmobility.corekotlin.utils.extensions

import java.util.regex.Pattern

/**
 * extension fun is used for checking if the string contains a number
 *
 *
 * @return boolean true for valid false for invalid
 */

fun String.containsNumber() = Pattern.compile("[0-9]").matcher(this).find()

/**
 * extension fun is used for checking valid emailCompleted id format.
 *
 *
 * @return boolean true for valid false for invalid
 */
fun String.isEmailValid(): Boolean {
    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String.countWords() = this.toLowerCase().replace(Regex("[^\\w']"), " ").trim().split(Regex("\\s+")).size