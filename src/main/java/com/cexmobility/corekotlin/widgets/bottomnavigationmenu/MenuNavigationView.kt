package com.cexmobility.corekotlin.widgets.bottomnavigationmenu

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.cexmobility.corekotlin.R

import java.util.Locale

import timber.log.Timber

class MenuNavigationView : LinearLayout {

    private var fragmentManager: FragmentManager? = null
    private var root: ConstraintLayout? = null
    private var base: ConstraintLayout? = null
    private var container: FrameLayout? = null
    private var items: List<MenuNavigationItem>? = null

    private var activeFragment: Fragment? = null

    val isMenuVisible: Boolean
        get() = if (this.base != null) {
            this.base!!.visibility == View.VISIBLE
        } else {
            false
        }


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.menu_nav_view, this)
        this.root = findViewById(R.id.menu_root)
        this.base = findViewById(R.id.menu_base)
        this.container = findViewById(R.id.menu_container)
        //this.menu = findViewById(R.id.menu_actions);
    }

    fun inflateItems(activity: FragmentActivity, items: List<MenuNavigationItem>) {
        // Initialize fragment manager
        this.fragmentManager = activity.supportFragmentManager

        // Initialize items
        this.items = items

        // Insert guidelines to place all menu elements
        for (i in 1 until items.size) {
            val gl = Guideline(context)
            val gllp = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            gllp.validate()
            val percent = 1f / items.size * i
            gllp.guidePercent = percent
            Timber.i("guide $i - $percent")
            gllp.orientation = LinearLayout.VERTICAL
            gl.layoutParams = gllp
            gl.id = View.generateViewId()
            gl.tag = String.format(Locale.ENGLISH, "tab_gl_%d", i)
            base!!.addView(gl)
        }

        // Insert all cells and place with constraint to guidelines
        for (i in items.indices) {
            val item = items[i]

            val cellView = View.inflate(context, R.layout.menu_nav_cell, null) as LinearLayout

            val layoutParams = ConstraintLayout.LayoutParams(0, 0)
            layoutParams.validate()

            when (i) {
                0 -> {
                    layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                    val tag = String.format(Locale.ENGLISH, "tab_gl_%d", i + 1)
                    layoutParams.endToStart = base!!.findViewWithTag<View>(tag).id
                }
                items.size - 1 -> {
                    val tag = String.format(Locale.ENGLISH, "tab_gl_%d", i)
                    layoutParams.startToStart = base!!.findViewWithTag<View>(tag).id
                    layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                }
                else -> {
                    val tagStart = String.format(Locale.ENGLISH, "tab_gl_%d", i)
                    val tagEnd = String.format(Locale.ENGLISH, "tab_gl_%d", i + 1)
                    layoutParams.startToEnd = base!!.findViewWithTag<View>(tagStart).id
                    layoutParams.endToStart = base!!.findViewWithTag<View>(tagEnd).id
                }
            }

            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID

            // Check if element must be sticking out above the separator
            when {
                !item.isMainOption -> layoutParams.topToBottom = base!!.findViewById<View>(R.id.separator).id
                else -> layoutParams.topToTop = base!!.findViewById<View>(R.id.menu_base).id
            }

            cellView.layoutParams = layoutParams
            cellView.id = View.generateViewId()
            val cellTag = String.format(Locale.ENGLISH, "tab_cell_%d", i)
            cellView.tag = cellTag

            val cellIcon = cellView.findViewById<ImageView>(R.id.mnu_cell_icon)
            cellIcon.setImageResource(item.icon)

            // Listener
            cellView.setOnClickListener { view -> setOnItemMenuListener(view, item) }

            // Add view
            base!!.addView(cellView)

            // Check fragment default
            when {
                item.isMainOption -> {
                    activeFragment = item.fragment
                    cellIcon.isSelected = true
                    configureFragmentSpace(item.isFullScreen)
                    fragmentManager!!.beginTransaction()
                        .add(R.id.menu_container, item.fragment!!, cellTag)
                        .commit()
                }
                else -> fragmentManager!!.beginTransaction()
                    .add(R.id.menu_container, item.fragment!!, cellTag)
                    .hide(item.fragment!!).commit()
            }
        }
    }

    private fun setOnItemMenuListener(view: View, navigationItem: MenuNavigationItem) {
        val tag = view.tag as String
        Timber.i("Pressed : %s", tag)

        for (i in this.items!!.indices) {
            val cellTag = String.format(Locale.ENGLISH, "tab_cell_%d", i)
            val ll = base!!.findViewWithTag<LinearLayout>(cellTag)
            val iconDisable = ll.findViewById<ImageView>(R.id.mnu_cell_icon)
            iconDisable.isSelected = false
        }

        val icon = view.findViewById<ImageView>(R.id.mnu_cell_icon)
        icon.isSelected = true

        this.selectItemMenu(navigationItem)
    }

    private fun selectItemMenu(navigationItem: MenuNavigationItem) {
        configureFragmentSpace(navigationItem.isFullScreen)
        this.fragmentManager!!.beginTransaction().hide(activeFragment!!)
            .show(navigationItem.fragment!!).commit()
        this.activeFragment = navigationItem.fragment
    }

    private fun configureFragmentSpace(isFullScreen: Boolean) {
        val layoutParams = container!!.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.validate()

        when {
            isFullScreen -> layoutParams.bottomToBottom = root!!.id
            else -> layoutParams.bottomToBottom = findViewById<View>(R.id.guideline_menu).id
        }

        container!!.layoutParams = layoutParams
    }

    fun showMenu(withAnimation: Boolean) {
        when {
            this.base != null && this.base!!.visibility == View.GONE -> // Hide menu
                this.base!!.visibility = View.VISIBLE
        }
    }

    fun showMenu(withAnimation: Boolean, delay: Long) {
        val handler = Handler()
        handler.postDelayed({ showMenu(withAnimation) }, delay)
    }

    fun hideMenu(withAnimation: Boolean) {
        when {
            this.base != null && this.base!!.visibility == View.VISIBLE -> // Hide menu
                this.base!!.visibility = View.GONE
        }
    }

    fun hideMenu(withAnimation: Boolean, delay: Long) {
        val handler = Handler()
        handler.postDelayed({ hideMenu(withAnimation) }, delay)
    }

}
