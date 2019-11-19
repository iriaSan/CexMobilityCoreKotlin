package com.cexmobility.corekotlin.utils.extensions


import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.cexmobility.corekotlin.R
import com.cexmobility.corekotlin.enums.ToolbarActions

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

fun ConstraintLayout.showAllViewsAnimated() {
    for (i in 0 until this.childCount) {
        val child = this.getChildAt(i)
        if (child is View) {
            child.show(i)
        }
    }
}