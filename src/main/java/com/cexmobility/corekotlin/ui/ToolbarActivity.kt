package com.cexmobility.corekotlin.ui


import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.cexmobility.corekotlin.R
import com.cexmobility.corekotlin.enums.ToolbarActions

abstract class ToolbarActivity : BaseActivity() {

    private var toolbar: Toolbar? = null
    private var toolbarTitle: TextView? = null
    private var toolbarSubtitle: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setToolbar(
        toolbar: Toolbar,
        toolbarAction: ToolbarActions,
        title: String,
        haveSubtitle: Boolean,
        subtitle: String
    ) {
        this.toolbar = toolbar
        this.toolbarTitle = findViewById(R.id.toolbar_title)
        this.toolbarSubtitle = findViewById(R.id.toolbar_subtitle)

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false)
            when (toolbarAction) {
                ToolbarActions.BACK -> {
                    actionBar.setDisplayHomeAsUpEnabled(true)
                    actionBar.setHomeAsUpIndicator(R.drawable.ic_arrowleft)
                }
                ToolbarActions.MENU -> {
                    actionBar.setDisplayHomeAsUpEnabled(true)
                    actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white)
                }
                ToolbarActions.CLOSE -> {
                    actionBar.setDisplayHomeAsUpEnabled(true)
                    actionBar.setHomeAsUpIndicator(R.drawable.ic_close_error)
                }
                ToolbarActions.NO_ACTION -> {
                }
                ToolbarActions.NONE -> {
                }
            }
        }

        when {
            !TextUtils.isEmpty(title) -> this.setToolbarTitle(title, haveSubtitle, subtitle)
        }
    }


    private fun setToolbarTitle(title: String, haveSubtitle: Boolean, subtitle: String) {
        this.toolbarTitle!!.text = title
        when {
            haveSubtitle -> {
                this.toolbarSubtitle!!.text = subtitle
                this.toolbarTitle!!.maxLines = 1
                this.toolbarSubtitle!!.visibility = View.VISIBLE
            }
            else -> {
                this.toolbarTitle!!.maxLines = 2
                this.toolbarSubtitle!!.visibility = View.GONE
            }
        }
    }

}
