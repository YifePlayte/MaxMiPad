package com.yifeplayte.maxmipadinput.hook

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.Log.logexIfThrow
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import com.yifeplayte.maxmipadinput.hook.hooks.android.*
import com.yifeplayte.maxmipadinput.util.Utils
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "MaxMiPadInput"
private val PACKAGE_NAME_HOOKED = setOf(
    "android",
)

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName in PACKAGE_NAME_HOOKED) {
            // Init EzXHelper
            EzXHelperInit.initHandleLoadPackage(lpparam)
            EzXHelperInit.setLogTag(TAG)
            EzXHelperInit.setToastTag(TAG)
            // Init hooks
            when (lpparam.packageName) {
                "android" -> {
                    if (Utils.getBoolean("no_magic_pointer", true)) {
                        initHooks(MiuiMagicPointerUtils)
                    }
                    if (Utils.getBoolean("restore_esc", true)) {
                        initHooks(SwitchPadMode)
                    }
                    if (Utils.getBoolean("remove_stylus_bluetooth_restriction", true)) {
                        initHooks(MiuiStylusDeviceListener)
                    }
                    if (Utils.getBoolean("ignore_stylus_key_gesture", true)) {
                        // initHooks(SupportStylusGesture)
                        initHooks(IsPageKeyEnable)
                    }
                }
            }
        }
    }

    private fun initHooks(vararg hook: BaseHook) {
        hook.forEach {
            runCatching {
                if (it.isInit) return@forEach
                it.init()
                it.isInit = true
                Log.i("Inited hook: ${it.javaClass.simpleName}")
            }.logexIfThrow("Failed init hook: ${it.javaClass.simpleName}")
        }
    }
}
