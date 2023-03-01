package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object SystemServerImpl : BaseHook() {
    override fun init() {
        try {
            findMethod("com.android.server.SystemServerImpl") {
                name == "addMagicPointerManagerService"
            }.hookReturnConstant(null)
            XposedBridge.log("MaxMiPad: Hook addMagicPointerManagerService success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxMiPad: Hook addMagicPointerManagerService failed!")
            XposedBridge.log(e)
        }
    }
}
