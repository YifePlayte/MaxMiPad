package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReturnConstant
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object BaseMiuiMultiFingerGesture : BaseHook() {
    override fun init() {
        findMethod("com.miui.server.input.gesture.multifingergesture.gesture.BaseMiuiMultiFingerGesture") {
            name == "getFunctionNeedFingerNum"
        }.hookReturnConstant(4)
    }
}
