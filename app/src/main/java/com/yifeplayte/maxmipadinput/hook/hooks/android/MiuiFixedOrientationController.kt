package com.yifeplayte.maxmipadinput.hook.hooks.android

import com.github.kyuubiran.ezxhelper.utils.findAllMethods
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import com.yifeplayte.maxmipadinput.util.Utils
import de.robv.android.xposed.XposedBridge

object MiuiFixedOrientationController : BaseHook() {
    override fun init() {
        try {
            findAllMethods("com.android.server.wm.MiuiFixedOrientationController") {
                name == "shouldDisableFixedOrientation"
            }.hookBefore { param ->
                if (Utils.getBoolean(
                        "disable_fixed_orientation_" + param.args[0] as String,
                        false
                    )
                ) {
                    param.result = true
                }
            }
            XposedBridge.log("MaxMiPadInput: Hook shouldDisableFixedOrientation success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxMiPadInput: Hook shouldDisableFixedOrientation failed!")
            XposedBridge.log(e)
        }
    }
}
