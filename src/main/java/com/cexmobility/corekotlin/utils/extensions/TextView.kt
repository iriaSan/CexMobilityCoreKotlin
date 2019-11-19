package com.cexmobility.corekotlin.utils.extensions


import android.view.View.OnFocusChangeListener
import android.widget.TextView

import android.view.View.OnFocusChangeListener
import android.widget.TextView

fun TextView.afterFocusChangedHasFocus(focus: (Boolean) -> Unit) {
    this.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
        focus.invoke(hasFocus)
    }
}