package com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadFirstClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object IgnoreStylusKeyGesture : BaseHook() {
    override val key = "ignore_stylus_key_gesture"
    override fun hook() {
        val clazzMiuiStylusPageKeyListener = loadFirstClass(
            "com.miui.server.input.stylus.MiuiStylusShortcutManager",
            "com.miui.server.input.stylus.MiuiStylusPageKeyListener",
            "com.miui.server.stylus.MiuiStylusPageKeyListener"
        )
        val methodNames =
            setOf("isPageKeyEnable", "needInterceptBeforeDispatching", "shouldInterceptKey")
        clazzMiuiStylusPageKeyListener.methodFinder().filter {
            name in methodNames
        }.toList().createHooks {
            returnConstant(false)
        }
    }
}