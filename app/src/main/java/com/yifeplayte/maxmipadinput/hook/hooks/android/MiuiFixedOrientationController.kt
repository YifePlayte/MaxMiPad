package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findAllMethods
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import com.yifeplayte.maxmipadinput.utils.XSharedPreferences

object MiuiFixedOrientationController : BaseHook() {
    override fun init() {
        val shouldDisableFixedOrientationList =
            XSharedPreferences.getStringSet("should_disable_fixed_orientation_list", mutableSetOf())
        findAllMethods("com.android.server.wm.MiuiFixedOrientationController") {
            name == "shouldDisableFixedOrientation"
        }.hookBefore { param ->
            if (param.args[0] in shouldDisableFixedOrientationList) {
                param.result = true
            }
        }
    }
}
