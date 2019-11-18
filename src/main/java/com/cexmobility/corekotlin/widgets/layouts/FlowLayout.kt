package com.cexmobility.corekotlin.widgets.layouts

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class FlowLayout : ViewGroup {

    private var line_height: Int = 0

    class LayoutParams
    /**
     * @param horizontal_spacing Pixels between items, horizontally
     * @param vertical_spacing Pixels between items, vertically
     */
        (val horizontal_spacing: Int, val vertical_spacing: Int) : ViewGroup.LayoutParams(0, 0)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        assert(MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED)

        val width = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        var height = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        val count = childCount
        var line_height = 0

        var xpos = paddingLeft
        var ypos = paddingTop

        val childHeightMeasureSpec: Int
        childHeightMeasureSpec = when {
            MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST -> MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)
            else -> MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        }


        for (i in 0 until count) {
            val child = getChildAt(i)
            when {
                child.visibility != View.GONE -> {
                    val lp = child.layoutParams as LayoutParams
                    child.measure(
                        MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                        childHeightMeasureSpec
                    )
                    val childw = child.measuredWidth
                    line_height = Math.max(line_height, child.measuredHeight + lp.vertical_spacing)

                    when {
                        xpos + childw > width -> {
                            xpos = paddingLeft
                            ypos += line_height
                        }
                    }

                    xpos += childw + lp.horizontal_spacing
                }
            }
        }
        this.line_height = line_height

        when {
            MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED -> height = ypos + line_height
            MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST -> if (ypos + line_height < height) {
                height = ypos + line_height
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(1, 1) // default of 1px spacing
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        //return new LayoutParams(1, 1, p);
        return LayoutParams(1, 1)
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val width = r - l
        var xpos = paddingLeft
        var ypos = paddingTop

        for (i in 0 until count) {
            val child = getChildAt(i)
            when {
                child.visibility != View.GONE -> {
                    val childw = child.measuredWidth
                    val childh = child.measuredHeight
                    val lp = child.layoutParams as LayoutParams
                    when {
                        xpos + childw > width -> {
                            xpos = paddingLeft
                            ypos += line_height
                        }
                    }
                    child.layout(xpos, ypos, xpos + childw, ypos + childh)
                    xpos += childw + lp.horizontal_spacing
                }
            }
        }
    }

}