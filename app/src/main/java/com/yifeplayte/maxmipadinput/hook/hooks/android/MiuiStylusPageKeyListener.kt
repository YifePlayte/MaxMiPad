package com.yifeplayte.maxmipadinput.hook.hooks.android

import android.os.Build
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import java.lang.reflect.Method

object MiuiStylusPageKeyListener : BaseHook() {
    override fun init() {
        val methods = mutableListOf<Method>()
        val className = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            "com.miui.server.input.stylus.MiuiStylusPageKeyListener"
        } else {
            "com.miui.server.stylus.MiuiStylusPageKeyListener"
        }
        val methodNames = setOf(
            "isPageKeyEnable", "needInterceptBeforeDispatching", "shouldInterceptKey"
        )
        methodNames.forEach { methodName ->
            kotlin.runCatching {
                findMethod(className) {
                    name == methodName
                }
            }.getOrNull()?.let { method ->
                methods.add(method)
            }
        }
        methods.hookReturnConstant(false)
    }
}