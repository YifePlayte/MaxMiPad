package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object BaseMiuiMultiFingerGesture : BaseHook() {
    override fun init() {
        try {
            findMethod("com.miui.server.input.gesture.multifingergesture.gesture.BaseMiuiMultiFingerGesture") {
                name == "getFunctionNeedFingerNum"
            }.hookReturnConstant(4)
            XposedBridge.log("MaxMiPad: Hook getFunctionNeedFingerNum success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxMiPad: Hook getFunctionNeedFingerNum failed!")
            XposedBridge.log(e)
        }
    }
}
