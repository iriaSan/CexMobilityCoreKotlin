package com.cexmobility.corekotlin.utils.extensions

import java.util.Currency
import java.util.Locale

fun Currency.symbol(locale: Locale): String? = Currency.getInstance(locale).symbol
fun Currency.symbol(): String? = Currency.getInstance(Locale.getDefault()).symbol