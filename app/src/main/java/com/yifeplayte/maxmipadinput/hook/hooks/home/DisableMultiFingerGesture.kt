package com.yifeplayte.maxmipadinput.hook.hooks.home

import android.view.InputDevice.SOURCE_TOUCHSCREEN
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

object DisableMultiFingerGesture : BaseHook() {
    override fun init() {
        loadClass("com.miui.home.recents.GestureModeApp").methodFinder()
            .filterByName("isSupportGestureOperation").first().createHook {
                before {
                    val motionEvent = it.args[2] as MotionEvent
                    val isTouchScreen =
                        motionEvent.device?.supportsSource(SOURCE_TOUCHSCREEN) ?: true
                    if (!isTouchScreen) return@before
                    if (motionEvent.pointerCount >= 3) it.result = false
                }
            }
    }
}