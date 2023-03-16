package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object SystemServerImpl : BaseHook() {
    override fun init() {
        findMethod("com.android.server.SystemServerImpl") {
            name == "addMagicPointerManagerService"
        }.hookReturnConstant(null)
    }
}
