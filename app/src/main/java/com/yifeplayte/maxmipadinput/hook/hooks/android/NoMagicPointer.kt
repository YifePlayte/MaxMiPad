package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClassOrNull
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object NoMagicPointer : BaseHook() {
    override fun init() {
        loadClassOrNull("android.magicpointer.util.MiuiMagicPointerUtils")?.methodFinder()?.filterByName("isEnable")
            ?.first()?.createHook {
                returnConstant(false)
            }
        loadClass("com.android.server.SystemServerImpl").methodFinder()
            .filterByName("addMagicPointerManagerService").first().createHook {
                returnConstant(null)
            }
    }
}
