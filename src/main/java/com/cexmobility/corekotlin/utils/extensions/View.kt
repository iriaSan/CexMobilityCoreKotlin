package com.cexmobility.corekotlin.utils.extensions

import android.view.View
import android.view.animation.AnimationUtils

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition


fun View.hide() {
    this.alpha = 1f
    this.scaleX = 1f
    this.animate()
            .alpha(0f)
            .scaleX(0f)
            .scaleY(0f)
            .setStartDelay(10).withEndAction { this.visibility = View.INVISIBLE }
            .setDuration(100).interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.fast_out_linear_in
    )
}

fun View.hide(delayMultiplier: Int) {
    this.alpha = 1f
    this.scaleX = 1f
    this.animate()
            .alpha(0f)
            .scaleX(0f)
            .scaleY(0f)
            .setStartDelay((50 * delayMultiplier).toLong()).withEndAction { this.visibility = View.INVISIBLE }
            .setDuration(150).interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.fast_out_linear_in
    )
}

fun View.show(delayMultiplier: Int?) {
    this.alpha = 0f
    this.scaleX = 0.8f
    this.scaleY = 0.8f
    var delay: Int? = 50
    if (delayMultiplier != null && delay != null) {
        delay *= delayMultiplier
        this.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setStartDelay(delay.toLong()).withStartAction { this.visibility = View.VISIBLE }
                .setDuration(1000).interpolator = AnimationUtils.loadInterpolator(
                context,
                android.R.interpolator.accelerate_cubic
        )
    }
}

fun View.show() {
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

fun View.show(duration: Long) {
    this.alpha = 0f
    this.scaleX = 0.8f
    this.scaleY = 0.8f
    this.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .withStartAction { this.visibility = View.VISIBLE }
            .setDuration(duration).interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.accelerate_cubic
    )
}

fun View.showTotal() {
    this.alpha = 0f
    this.scaleX = 1f
    this.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setStartDelay(100).withStartAction { this.visibility = View.VISIBLE }
            .setDuration(200).interpolator = AnimationUtils.loadInterpolator(
            context,
            android.R.interpolator.fast_out_linear_in
    )
}

fun View.changeColorAnimatedly(duration: Long, repeatable: Boolean, startColor: Int, endColor: Int) {
    val anim = ObjectAnimator.ofInt(this, "backgroundColor", startColor, endColor)
    anim.duration = duration
    anim.setEvaluator(ArgbEvaluator())
    when {
        repeatable -> {
            anim.repeatCount = ValueAnimator.INFINITE
            anim.repeatMode = ValueAnimator.REVERSE
        }
    }
    anim.start()
}

fun ViewGroup.inflate(layoutRes: Int): View = LayoutInflater.from(context).inflate(layoutRes, this, false)

fun View.cancelTransition() {
    transitionName = null
}

fun ImageView.loadFromUrl(url: String) =
        Glide.with(this.context.applicationContext)
                .load(url).apply(RequestOptions().centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)

fun ImageView.loadUrlAndPostponeEnterTransition(url: String, activity: FragmentActivity) {
    val target: Target<Drawable> = ImageViewBaseTarget(
            this,
            activity
    )
    Glide.with(context.applicationContext).load(url).into(target)
}

fun View.changeColor(fraction: Float, to: Int) {
    this.setBackgroundColor(interpolateColor(fraction, this.solidColor, to))
}

private fun interpolateColor(fraction: Float, startValue: Int, endValue: Int): Int {
    val startA = startValue shr 24 and 0xff
    val startR = startValue shr 16 and 0xff
    val startG = startValue shr 8 and 0xff
    val startB = startValue and 0xff
    val endA = endValue shr 24 and 0xff
    val endR = endValue shr 16 and 0xff
    val endG = endValue shr 8 and 0xff
    val endB = endValue and 0xff
    return startA + (fraction * (endA - startA)).toInt() shl 24 or
            (startR + (fraction * (endR - startR)).toInt() shl 16) or
            (startG + (fraction * (endG - startG)).toInt() shl 8) or
            startB + (fraction * (endB - startB)).toInt()
}

private class ImageViewBaseTarget(var imageView: ImageView?, var activity: FragmentActivity?) : BaseTarget<Drawable>() {
    override fun removeCallback(cb: SizeReadyCallback) {
        imageView = null
        activity = null
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        imageView?.setImageDrawable(resource)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun getSize(cb: SizeReadyCallback) = cb.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL)

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }
}

fun View.gone() {
    when {
        this.visibility == View.VISIBLE -> this.visibility = View.GONE
    }
}

fun View.visible() {
    when {
        this.visibility == View.GONE || this.visibility == View.INVISIBLE -> this.visibility = View.VISIBLE
    }
}

fun View.invisible() {
    when {
        this.visibility == View.VISIBLE -> this.visibility = View.INVISIBLE
    }
}