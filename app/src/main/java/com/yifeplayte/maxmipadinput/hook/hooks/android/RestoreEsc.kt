package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object RestoreEsc : BaseHook() {
    override fun init() {
        ClassUtils.loadClass("com.android.server.input.config.InputCommonConfig").methodFinder()
            .filterByName("setPadMode").first().createHook {
                before {
                    it.args[0] = false
                }
            }
        ClassUtils.loadClass("com.android.server.input.InputManagerServiceStubImpl").methodFinder()
            .filterByName("switchPadMode").first().createHook {
                before {
                    it.args[0] = false
                }
            }
    }
}
