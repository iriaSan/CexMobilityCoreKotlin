package com.cexmobility.corekotlin.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

fun AppCompatActivity.toggleHideShowKeyboard() {
    val inputMethodManager = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    when {
        inputMethodManager.isActive -> inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        else -> inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0) // hide
    }
}


fun AppCompatActivity.hideKeyBoard() {
    val view = this.currentFocus
    if (view != null) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun AppCompatActivity.changeStatusBarColor(color: Int) {
    val window = this.window
    window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window?.statusBarColor = ContextCompat.getColor(this, color)
}

/* The function finishes all the activities except for FirstActivity. Then we need to finish the FirstActivity's Enter the below code in Firstactivity's oncreate
if (getIntent().getBooleanExtra("EXIT", false)) {
    finish()}*/
fun AppCompatActivity.finishAllActivities() {
    val intent = Intent(applicationContext, this::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    intent.putExtra("EXIT", true)
    startActivity(intent)
}

fun AppCompatActivity.closeActivitiesAndGoTo(klass: Class<*>) {
    // Use like --->
    // closeActivitiesAndGoTo(this.activity,SplashActivity::class.java)
    val intent = Intent(this, klass)
    val cn = intent.component
    val mainIntent = Intent.makeRestartActivityTask(cn)
    this.startActivity(mainIntent)
}