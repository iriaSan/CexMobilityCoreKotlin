package com.cexmobility.corekotlin.widgets.bottomnavigationmenu

import androidx.fragment.app.Fragment

class MenuNavigationItem {

    var index: Int = 0
    var name: String? = null
    var icon: Int = 0
    var fragment: Fragment? = null
    var isMainOption: Boolean = false
    var isFullScreen: Boolean = false

    constructor(index: Int, name: String, icon: Int, fragment: Fragment) {
        this.index = index
        this.name = name
        this.icon = icon
        this.fragment = fragment
        this.isMainOption = false
        this.isFullScreen = false
    }

    constructor(index: Int, name: String, icon: Int, fragment: Fragment, isMainOption: Boolean) {
        this.index = index
        this.name = name
        this.icon = icon
        this.fragment = fragment
        this.isMainOption = isMainOption
        this.isFullScreen = false
    }

    constructor(
        index: Int,
        name: String,
        icon: Int,
        fragment: Fragment,
        isMainOption: Boolean,
        isFullScreen: Boolean
    ) {
        this.index = index
        this.name = name
        this.icon = icon
        this.fragment = fragment
        this.isMainOption = isMainOption
        this.isFullScreen = isFullScreen
    }
}
