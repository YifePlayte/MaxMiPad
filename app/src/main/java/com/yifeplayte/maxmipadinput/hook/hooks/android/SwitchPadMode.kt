package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object SwitchPadMode : BaseHook() {
    override fun init() {
        findMethod("com.android.server.input.InputManagerServiceStubImpl") {
            name == "switchPadMode"
        }.hookBefore { param ->
            param.args[0] = false
        }
    }
}
