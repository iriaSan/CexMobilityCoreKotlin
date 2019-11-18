package com.iriasan.woomtest.utils.extensions

import android.view.View
import android.view.animation.AnimationUtils

fun View.show(){
    this.alpha = 0f
    this.scaleX = 0.8f
    this.scaleY = 0.8f
    this.animate()
        .alpha(1f)
        .scaleX(1f)
        .scaleY(1f)
        .withStartAction { this.visibility = View.VISIBLE }
        .setDuration(200).interpolator = AnimationUtils.loadInterpolator(
        context,
        android.R.interpolator.accelerate_cubic
    )

}

fun View.hide() {
    this.alpha = 1f
    this.scaleX = 1f
    this.animate()
        .alpha(0f)
        .scaleX(0f)
        .scaleY(0f)
        .setStartDelay((150).toLong()).withEndAction { this.visibility = View.INVISIBLE }
        .setDuration(250).interpolator = AnimationUtils.loadInterpolator(
        context,
        android.R.interpolator.fast_out_linear_in
    )
}