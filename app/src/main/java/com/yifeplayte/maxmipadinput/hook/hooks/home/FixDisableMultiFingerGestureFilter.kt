package com.yifeplayte.maxmipadinput.hook.hooks.home

import android.os.Build
import android.view.InputDevice.SOURCE_TOUCHSCREEN
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassHelper.Companion.classHelper
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object FixDisableMultiFingerGestureFilter : BaseHook() {
    override fun init() {
        loadClass("com.miui.home.recents.GestureModeApp").methodFinder()
            .filterByName("isSupportGestureOperation").first().createHook {
                before {
                    val recentsImpl = loadClass("com.miui.home.launcher.Application").classHelper()
                        .invokeStaticMethodBestMatch("getLauncherApplication")?.objectHelper()
                        ?.invokeMethodBestMatch("getRecentsImpl")

                    val isMultiFingerSlideSupport = recentsImpl?.objectHelper()
                        ?.invokeMethodBestMatch("isMultiFingerSlideSupport") == true
                    if (isMultiFingerSlideSupport) return@before

                    val motionEvent = it.args[2] as MotionEvent
                    val isTouchScreen =
                        motionEvent.device?.supportsSource(SOURCE_TOUCHSCREEN) ?: true
                    if (!isTouchScreen) return@before
                    if (motionEvent.pointerCount < 3) return@before

                    it.result = !isImmersive(recentsImpl)
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