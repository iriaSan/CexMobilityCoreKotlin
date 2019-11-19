package com.cexmobility.corekotlin.utils.extensions

import android.content.res.ColorStateList
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.textfield.TextInputLayout
import com.cexmobility.corekotlin.R

fun TextInputLayout.setErrorAppearanceExt() {
    setInputLayoutColors(this, R.color.red_error)
    setEditTextColors(this.editText, R.color.red_error)
}

fun TextInputLayout.setDefaultAppearanceExt() {
    setInputLayoutColors(this, R.color.colorWhite)
    setEditTextColors(this.editText, R.color.colorWhite)
}

private fun setInputLayoutColors(
        inputLayout: TextInputLayout?,
        @ColorRes colorResourceId: Int
) {

    if (inputLayout == null) {
        return
    }
    val color = ContextCompat.getColor(inputLayout.context, colorResourceId)
    try {
        val defaultTextColor = TextInputLayout::class.java.getDeclaredField("mDefaultTextColor")
        defaultTextColor.isAccessible = true
        defaultTextColor.set(inputLayout, ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(color)))

        val focusedTextColor = TextInputLayout::class.java.getDeclaredField("mFocusedTextColor")
        focusedTextColor.isAccessible = true
        focusedTextColor.set(inputLayout, ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(color)))
    } catch (e: Exception) {
        e.printStackTrace()
    }
    inputLayout.refreshDrawableState()
}

private fun setEditTextColors(
        editText: EditText?,
        @ColorRes colorResourceId: Int
) {

    if (editText == null) {
        return
    }
    val color = ContextCompat.getColor(editText.context, colorResourceId)
    DrawableCompat.setTintList(editText.background.mutate(), createStateList(color))
}

private fun createStateList(@ColorInt color: Int): ColorStateList {
    val states =
            arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf(-android.R.attr.state_focused), intArrayOf())

    val colors = intArrayOf(color, color, color)

    return ColorStateList(states, colors)
}