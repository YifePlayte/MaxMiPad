package com.yifeplayte.maxmipadinput.hook.hooks.singlepackage

import com.yifeplayte.maxmipadinput.hook.hooks.BasePackage
import com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.home.FixDisableMultiFingerGestureFilter

object Home : BasePackage() {
    override val packageName = "com.miui.home"
    override val hooks = setOf(
        FixDisableMultiFingerGestureFilter,
    )
}