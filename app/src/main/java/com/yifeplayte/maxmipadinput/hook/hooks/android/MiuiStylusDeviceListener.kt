package com.yifeplayte.maxmipadinput.hook.hooks.android

import android.os.Build
import com.github.kyuubiran.ezxhelper.utils.*
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object MiuiStylusDeviceListener : BaseHook() {
    override fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            findAllConstructors("com.miui.server.input.stylus.MiuiStylusDeviceListener") { true }.hookAfter {
                val ITouchFeature = loadClass("miui.util.ITouchFeature")
                val mTouchFeature = findMethod(ITouchFeature) {
                    name == "getInstance"
                }.invoke(null)
                mTouchFeature?.invokeMethod(
                    "setTouchMode",
                    args(0, 20, 0x12),
                    argTypes(Int::class.java, Int::class.java, Int::class.java)
                )
            }
            findAllMethods("com.miui.server.input.stylus.MiuiStylusDeviceListener") { true }.hookReplace {
                val ITouchFeature = loadClass("miui.util.ITouchFeature")
                val mTouchFeature = findMethod(ITouchFeature) {
                    name == "getInstance"
                }.invoke(null)
                mTouchFeature?.invokeMethod(
                    "setTouchMode",
                    args(0, 20, 0x12),
                    argTypes(Int::class.java, Int::class.java, Int::class.java)
                )
            }
        } else {
            findAllConstructors("com.miui.server.stylus.MiuiStylusDeviceListener") { true }.hookAfter {
                val ITouchFeature = loadClass("miui.util.ITouchFeature")
                val mTouchFeature = findMethod(ITouchFeature) {
                    name == "getInstance"
                }.invoke(null)
                mTouchFeature?.invokeMethod(
                    "setTouchMode",
                    args(0, 20, 1),
                    argTypes(Int::class.java, Int::class.java, Int::class.java)
                )
            }
            findAllMethods("com.miui.server.stylus.MiuiStylusDeviceListener") { true }.hookReplace {
                val ITouchFeature = loadClass("miui.util.ITouchFeature")
                val mTouchFeature = findMethod(ITouchFeature) {
                    name == "getInstance"
                }.invoke(null)
                mTouchFeature?.invokeMethod(
                    "setTouchMode",
                    args(0, 20, 1),
                    argTypes(Int::class.java, Int::class.java, Int::class.java)
                )
            }
        }
    }
}
