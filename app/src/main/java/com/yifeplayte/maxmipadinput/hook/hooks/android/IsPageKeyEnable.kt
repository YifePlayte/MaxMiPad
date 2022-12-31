package com.yifeplayte.maxmipadinput.hook.hooks.android

import android.os.Build
import com.github.kyuubiran.ezxhelper.utils.*
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object IsPageKeyEnable : BaseHook() {
    override fun init() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                findMethod("com.miui.server.input.stylus.MiuiStylusPageKeyListener") {
                    name == "isPageKeyEnable"
                }.hookReturnConstant(false)
            } else {
                findMethod("com.miui.server.stylus.MiuiStylusPageKeyListener") {
                    name == "isPageKeyEnable"
                }.hookReturnConstant(false)
            }
            XposedBridge.log("MaxMiPadInput: Hook isPageKeyEnable success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxMiPadInput: Hook isPageKeyEnable failed!")
        }
    }
}