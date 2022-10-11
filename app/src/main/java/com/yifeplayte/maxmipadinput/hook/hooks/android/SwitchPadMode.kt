package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object SwitchPadMode : BaseHook() {
    override fun init() {
        try {
            findMethod("com.android.server.input.InputManagerServiceStubImpl") {
                name == "switchPadMode"
            }.hookBefore { param ->
                param.args[0] = false
            }
            XposedBridge.log("MaxMiPadInput: Hook switchPadMode success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxMiPadInput: Hook switchPadMode failed!")
        }
    }
}
