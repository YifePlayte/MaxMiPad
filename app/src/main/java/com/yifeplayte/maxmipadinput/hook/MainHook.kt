package com.yifeplayte.maxmipadinput.hook

import com.github.kyuubiran.ezxhelper.EzXHelper
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.LogExtensions.logexIfThrow
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import com.yifeplayte.maxmipadinput.hook.hooks.android.*
import com.yifeplayte.maxmipadinput.hook.hooks.home.FixDisableMultiFingerGestureFilter
import com.yifeplayte.maxmipadinput.hook.hooks.multiple.SetGestureNeedFingerNumTo4
import com.yifeplayte.maxmipadinput.hook.utils.DexKit
import com.yifeplayte.maxmipadinput.hook.utils.XSharedPreferences.getBoolean
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "MaxMiPad"
val PACKAGE_NAME_HOOKED = setOf(
    "android",
    "com.miui.home",
)

@Suppress("unused")
class MainHook : IXposedHookLoadPackage, IXposedHookZygoteInit {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName in PACKAGE_NAME_HOOKED) {

            // init DexKit and EzXHelper
            if (lpparam.packageName != "android") DexKit.initDexKit(lpparam)
            EzXHelper.initHandleLoadPackage(lpparam)
            EzXHelper.setLogTag(TAG)
            EzXHelper.setToastTag(TAG)

            // Init hooks
            when (lpparam.packageName) {
                "android" -> {
                    initHook(NoMagicPointer, "no_magic_pointer", true)
                    initHook(RestoreEsc, "restore_esc", true)
                    initHook(RemoveStylusBluetoothRestriction, "remove_stylus_bluetooth_restriction", true)
                    initHook(IgnoreStylusKeyGesture, "ignore_stylus_key_gesture")
                    initHook(DisableFixedOrientation, "disable_fixed_orientation", true)
                    initHook(SetGestureNeedFingerNumTo4, "set_gesture_need_finger_num_to_4")
                }

                "com.miui.home" -> {
                    initHook(SetGestureNeedFingerNumTo4, "set_gesture_need_finger_num_to_4")
                    initHook(FixDisableMultiFingerGestureFilter,"fix_disable_multi_finger_gesture_filter")
                }
            }

            DexKit.closeDexKit()
        }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelper.initZygote(startupParam)
    }

    private fun initHook(hook: BaseHook, key: String, defValue: Boolean = false) =
        initHook(hook, getBoolean(key, defValue))

    private fun initHook(hook: BaseHook, enable: Boolean = true) {
        if (enable) runCatching {
            if (hook.isInit) return
            hook.init()
            hook.isInit = true
            Log.ix("Inited hook: ${hook.javaClass.simpleName}")
        }.logexIfThrow("Failed init hook: ${hook.javaClass.simpleName}")
    }
}
