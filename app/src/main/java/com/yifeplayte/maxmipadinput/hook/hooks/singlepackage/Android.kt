package com.yifeplayte.maxmipadinput.hook.hooks.singlepackage

import com.yifeplayte.maxmipadinput.hook.hooks.BasePackage
import com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.android.DisableFixedOrientation
import com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.android.IgnoreStylusKeyGesture
import com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.android.NoMagicPointer
import com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.android.RemoveStylusBluetoothRestriction
import com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.android.RestoreEsc

object Android : BasePackage() {
    override val packageName = "android"
    override val hooks = setOf(
        DisableFixedOrientation,
        IgnoreStylusKeyGesture,
        NoMagicPointer,
        RemoveStylusBluetoothRestriction,
        RestoreEsc,
    )
}