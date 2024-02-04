package com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.android

import android.os.Build
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.ClassUtils.loadFirstClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import com.yifeplayte.maxmipadinput.hook.utils.XSharedPreferences.getString

object RemoveStylusBluetoothRestriction : BaseHook() {
    override val key = "remove_stylus_bluetooth_restriction"
    override fun hook() {
        val isNew = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
        val clazzMiuiStylusDeviceListener = loadFirstClass(
            "com.miui.server.input.stylus.MiuiStylusDeviceListener",
            "com.miui.server.stylus.MiuiStylusDeviceListener"
        )
        clazzMiuiStylusDeviceListener.declaredConstructors.createHooks {
            after {
                setTouchModeStylusEnable(isNew)
            }
        }
        clazzMiuiStylusDeviceListener.declaredMethods.createHooks {
            replace {
                setTouchModeStylusEnable(isNew)
            }
        }
    }

    private fun setTouchModeStylusEnable(isNew: Boolean) {
        val driverVersion =
            getString("remove_stylus_bluetooth_restriction_driver_version", "2").toInt()
        val flag: Int = if (isNew) (0x10 or driverVersion) else 1
        val instanceITouchFeature =
            invokeStaticMethodBestMatch(loadClass("miui.util.ITouchFeature"), "getInstance")!!
        invokeMethodBestMatch(
            instanceITouchFeature,
            "setTouchMode",
            null,
            0, 20, flag
        )
    }
}
