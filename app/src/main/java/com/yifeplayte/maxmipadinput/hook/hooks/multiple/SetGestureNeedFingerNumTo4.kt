package com.yifeplayte.maxmipadinput.hook.hooks.multiple

import android.os.Build
import android.view.InputDevice.SOURCE_TOUCHSCREEN
import android.view.MotionEvent
import com.github.kyuubiran.ezxhelper.ClassUtils
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseMultiHook

object SetGestureNeedFingerNumTo4 : BaseMultiHook() {
    override val key = "set_gesture_need_finger_num_to_4"
    override val hooks = mapOf(
        "android" to { android() },
        "com.miui.home" to { home() },
    )

    private fun android() {
        loadClass("com.miui.server.input.gesture.multifingergesture.gesture.BaseMiuiMultiFingerGesture").methodFinder()
            .filterByName("getFunctionNeedFingerNum").first().createHook {
                returnConstant(4)
            }
    }

    private fun home() {
        val clazzGestureOperationHelper = loadClass("com.miui.home.recents.GestureOperationHelper")
        clazzGestureOperationHelper.methodFinder()
            .filterByName("isThreePointerSwipeLeftOrRightInScreen")
            .filterByParamTypes(MotionEvent::class.java, Int::class.java).first().createHook {
                before { param ->
                    val motionEvent = param.args[0] as MotionEvent
                    val swipeFlag = param.args[1] as Int
                    val flagSwipeLeft = ClassUtils.getStaticObjectOrNullAs<Int>(
                        clazzGestureOperationHelper, "SWIPE_DIRECTION_LEFT"
                    )
                    val flagSwipeRight = ClassUtils.getStaticObjectOrNullAs<Int>(
                        clazzGestureOperationHelper, "SWIPE_DIRECTION_RIGHT"
                    )
                    val flagsSwipeLeftAndRight = setOf(flagSwipeLeft, flagSwipeRight)
                    val isTouchScreen =
                        motionEvent.device?.supportsSource(SOURCE_TOUCHSCREEN) ?: true
                    param.result =
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && isTouchScreen && (swipeFlag in flagsSwipeLeftAndRight) && motionEvent.pointerCount == 4
                }
            }
    }
}
