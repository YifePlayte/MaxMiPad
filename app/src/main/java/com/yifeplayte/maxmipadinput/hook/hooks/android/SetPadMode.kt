package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object SetPadMode : BaseHook() {
    override fun init() {
        findMethod("com.android.server.input.config.InputCommonConfig") {
            name == "setPadMode"
        }.hookBefore { param ->
            // Log.ix("MaxMiPadInputTest: setPadMode called!")
            param.args[0] = false
        }
    }
}
