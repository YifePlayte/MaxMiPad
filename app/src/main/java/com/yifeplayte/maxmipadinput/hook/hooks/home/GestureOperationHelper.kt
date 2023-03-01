package com.yifeplayte.maxmipadinput.hook.hooks.home

import android.os.Build
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.utils.*
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook
import de.robv.android.xposed.XposedBridge

object GestureOperationHelper : BaseHook() {
    override fun init() {
        try {
            val clazzGestureOperationHelper =
                loadClass("com.miui.home.recents.GestureOperationHelper")
            findMethod(clazzGestureOperationHelper) {
                name == "isThreePointerSwipeLeftOrRightInScreen" &&
                        paramCount == 2 &&
                        parameterTypes[0] == MotionEvent::class.java &&
                        parameterTypes[1] == Int::class.java
            }.hookBefore { param ->
                val motionEvent = param.args[0] as MotionEvent
                val swipeFlag = param.args[1] as Int
                val flagSwipeLeft = clazzGestureOperationHelper.field("SWIPE_DIRECTION_LEFT", true)
                    .getInt(null)
                val flagSwipeRight = clazzGestureOperationHelper.field("SWIPE_DIRECTION_RIGHT", true)
                    .getInt(null)
                val flagsSwipeLeftAndRight = setOf(flagSwipeLeft, flagSwipeRight)
                val z = if (motionEvent.device == null) {
                    true
                } else {
                    motionEvent.device.sources and 4098 == 4098
                }
                param.result =
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && z && (swipeFlag in flagsSwipeLeftAndRight) && motionEvent.pointerCount == 4
            }
            XposedBridge.log("MaxMiPad: Hook registerThreeGestureObserver success!")
        } catch (e: Throwable) {
            XposedBridge.log("MaxMiPad: Hook registerThreeGestureObserver failed!")
            XposedBridge.log(e)
        }
    }
}