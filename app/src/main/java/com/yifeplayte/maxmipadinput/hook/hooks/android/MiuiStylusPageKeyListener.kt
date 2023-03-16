package com.yifeplayte.maxmipadinput.hook.hooks.android

import android.os.Build
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object MiuiStylusPageKeyListener : BaseHook() {
    override fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            findMethod("com.miui.server.input.stylus.MiuiStylusPageKeyListener") {
                name == "isPageKeyEnable"
            }.hookReturnConstant(false)
        } else {
            findMethod("com.miui.server.stylus.MiuiStylusPageKeyListener") {
                name == "isPageKeyEnable"
            }.hookReturnConstant(false)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            findMethod("com.miui.server.input.stylus.MiuiStylusPageKeyListener") {
                name == "needInterceptBeforeDispatching"
            }.hookReturnConstant(false)
        } else {
            findMethod("com.miui.server.stylus.MiuiStylusPageKeyListener") {
                name == "needInterceptBeforeDispatching"
            }.hookReturnConstant(false)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            findMethod("com.miui.server.input.stylus.MiuiStylusPageKeyListener") {
                name == "shouldInterceptKey"
            }.hookReturnConstant(false)
        } else {
            findMethod("com.miui.server.stylus.MiuiStylusPageKeyListener") {
                name == "shouldInterceptKey"
            }.hookReturnConstant(false)
        }
    }
}