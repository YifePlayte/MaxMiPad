package com.yifeplayte.maxmipadinput.hook.utils

import com.github.kyuubiran.ezxhelper.ClassUtils
import java.lang.reflect.Member

object XposedBridge {
    @JvmStatic
    fun Member.deoptimizeMethod() {
        ClassUtils.invokeStaticMethodBestMatch(
            de.robv.android.xposed.XposedBridge::class.java,
            "deoptimizeMethod",
            null,
            this
        )
    }
}