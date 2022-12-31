package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object SetPadMode : BaseHook() {
    override fun init() {
        try {
            findMethod("com.android.server.input.config.InputCommonConfig") {
                name == "setPadMode"
            }.hookBefore { param ->
                // XposedBridge.log("MaxMiPadInputTest: setPadMode called!")
                param.args[0] = false
            }
            XposedBridge.log("MaxMiPadInput: Hook setPadMode success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxMiPadInput: Hook setPadMode failed!")
        }
    }
}
