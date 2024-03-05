package com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.android

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.MemberExtensions.isNotAbstract
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import com.yifeplayte.maxmipadinput.hook.utils.XposedBridge.deoptimizeMethod

object RestoreEsc : BaseHook() {
    override val key = "restore_esc"
    override fun hook() {
        loadClass("com.android.server.input.InputManagerServiceStubImpl").methodFinder()
            .filterByName("switchPadMode").single().deoptimizeMethod()
        loadClass("com.android.server.input.InputManagerServiceStubImpl").methodFinder()
            .filterByName("init").filter { this.isNotAbstract }.single().deoptimizeMethod()
        loadClass("com.android.server.input.config.InputCommonConfig").methodFinder()
            .filterByName("setPadMode").single().createHook {
                before {
                    it.args[0] = false
                }
            }
    }
}
