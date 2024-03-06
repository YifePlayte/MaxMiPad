package com.yifeplayte.maxmipadinput.hook.hooks.singlepackage.home

import android.os.Build
import android.provider.Settings
import android.view.InputDevice.SOURCE_TOUCHSCREEN
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassHelper.Companion.classHelper
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.appContext
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object FixDisableMultiFingerGestureFilter : BaseHook() {
    override val key = "fix_disable_multi_finger_gesture_filter"
    private val isThreeGestureEnabled: Boolean
        get() = Settings.System.getString(
            appContext.contentResolver, "enable_three_gesture"
        ).toInt() > 0

    override fun hook() {
        loadClass("com.miui.home.recents.GestureModeApp").methodFinder()
            .filterByName("isSupportGestureOperation").first().createHook {
                before {
                    val recentsImpl = loadClass("com.miui.home.launcher.Application").classHelper()
                        .invokeStaticMethodBestMatch("getLauncherApplication")?.objectHelper()
                        ?.invokeMethodBestMatch("getRecentsImpl")

                    val isMultiFingerSlideSupport = recentsImpl?.objectHelper()
                        ?.invokeMethodBestMatch("isMultiFingerSlideSupport") == true
                    if (isMultiFingerSlideSupport && isThreeGestureEnabled) return@before

                    val motionEvent = it.args[2] as MotionEvent
                    val isTouchScreen =
                        motionEvent.device?.supportsSource(SOURCE_TOUCHSCREEN) ?: true
                    if (!isTouchScreen) return@before
                    if (motionEvent.pointerCount < 3) return@before

                    it.result = isThreeGestureEnabled && !isImmersive(recentsImpl)
                }
            }
    }

    private fun isImmersive(recentsImpl: Any?): Boolean {
        if (recentsImpl == null) return false

        val isShowNavBar = invokeMethodBestMatch(recentsImpl, "isShowNavBar") == true
        val isShowStatusBar = invokeMethodBestMatch(recentsImpl, "isShowStatusBar") == true
        val isHideGestureLine = invokeMethodBestMatch(recentsImpl, "isHideGestureLine") == true

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) !isShowNavBar
        else if (isHideGestureLine) !isShowStatusBar
        else !isShowNavBar
    }
}