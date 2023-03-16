package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object MiuiMagicPointerUtils : BaseHook() {
    override fun init() {
        findMethod("android.magicpointer.util.MiuiMagicPointerUtils") {
            name == "isEnable"
        }.hookReturnConstant(false)
    }
}
