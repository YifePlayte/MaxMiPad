package com.yifeplayte.maxmipadinput.hook.hooks.android

import android.os.Build
import com.github.kyuubiran.ezxhelper.utils.*
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object SupportStylusGesture : BaseHook() {
    override fun init() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                findMethod("com.miui.server.input.stylus.MiuiStylusUtils") {
                    name == "supportStylusGesture"
                }.hookReturnConstant(false)
            } else {
                findMethod("com.miui.server.stylus.MiuiStylusUtils") {
                    name == "supportStylusGesture"
                }.hookReturnConstant(false)
            }
            findMethod("com.android.server.policy.BaseMiuiPhoneWindowManager") {
                name == "initInternal"
            }.hookAfter {
                val ITouchFeature = loadClass("miui.util.ITouchFeature")
                val mTouchFeature = findMethod(ITouchFeature) {
                    name == "getInstance"
                }.invoke(null)
                findMethod(ITouchFeature) {
                    name == "setTouchMode" && paramCount == 3
                }.invoke(mTouchFeature, 0, 20, 1)
            }
            XposedBridge.log("MaxMiPadInput: Hook supportStylusGesture success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxMiPadInput: Hook supportStylusGesture failed!")
        }
    }
}