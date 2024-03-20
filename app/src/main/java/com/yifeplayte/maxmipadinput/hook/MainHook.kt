package com.yifeplayte.maxmipadinput.hook

import com.github.kyuubiran.ezxhelper.EzXHelper
import com.yifeplayte.maxmipadinput.hook.hooks.multiple.EnableStylusInputMethod
import com.yifeplayte.maxmipadinput.hook.hooks.multiple.SetGestureNeedFingerNumTo4
import com.yifeplayte.maxmipadinput.hook.hooks.multiple.VerticalSplitOnPortrait
import com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.Android
import com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.Home
import com.yifeplayte.maxmipadinput.hook.utils.DexKit
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

private const val TAG = "MaxMiPad"
private val singlePackagesHooked = setOf(
    Android,
    Home,
)
private val multiPackagesHooked = setOf(
    EnableStylusInputMethod,
    SetGestureNeedFingerNumTo4,
    VerticalSplitOnPortrait,
)
val PACKAGE_NAME_HOOKED: Set<String>
    get() {
        val packageNameHooked = mutableSetOf<String>()
        singlePackagesHooked.forEach { packageNameHooked.add(it.packageName) }
        multiPackagesHooked.forEach { packageNameHooked.addAll(it.hooks.keys) }
        return packageNameHooked
    }

@Suppress("unused")
class MainHook : IXposedHookLoadPackage, IXposedHookZygoteInit {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName in PACKAGE_NAME_HOOKED) {

            // init DexKit and EzXHelper
            if (lpparam.isFirstApplication) {
                if (lpparam.packageName != "android") DexKit.initDexKit(lpparam)
                EzXHelper.initHandleLoadPackage(lpparam)
                EzXHelper.setLogTag(TAG)
                EzXHelper.setToastTag(TAG)
            }

            // single package
            singlePackagesHooked.forEach { it.init() }

            // multiple package
            multiPackagesHooked.forEach { it.init() }

            DexKit.closeDexKit()
        }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelper.initZygote(startupParam)
    }
}
