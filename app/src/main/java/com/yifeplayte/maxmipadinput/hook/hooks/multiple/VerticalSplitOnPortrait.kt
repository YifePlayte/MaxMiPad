package com.yifeplayte.maxmipadinput.hook.hooks.multiple

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.github.kyuubiran.ezxhelper.ClassHelper.Companion.classHelper
import com.github.kyuubiran.ezxhelper.ClassUtils.invokeStaticMethodBestMatch
import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.appContext
import com.github.kyuubiran.ezxhelper.EzXHelper.hostPackageName
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.Log
import com.github.kyuubiran.ezxhelper.MemberExtensions.isStatic
import com.github.kyuubiran.ezxhelper.ObjectHelper.Companion.objectHelper
import com.github.kyuubiran.ezxhelper.ObjectUtils.getObjectOrNull
import com.github.kyuubiran.ezxhelper.ObjectUtils.invokeMethodBestMatch
import com.github.kyuubiran.ezxhelper.ObjectUtils.setObject
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseHook

@SuppressLint("DiscouragedApi")
object VerticalSplitOnPortrait : BaseHook() {
    private val boolConfigMiuiSplitFeatureEnable by lazy {
        appContext.resources.getIdentifier(
            "config_miui_split_feature_enable", "bool", hostPackageName
        )
    }
    private val drawableIcTaskMultiPad by lazy {
        appContext.resources.getIdentifier(
            "ic_task_multi_pad", "drawable", hostPackageName
        )
    }
    private val drawableIcTaskMulti by lazy {
        appContext.resources.getIdentifier(
            "ic_task_multi", "drawable", hostPackageName
        )
    }

    override fun init() {
        when (hostPackageName) {
            "com.android.systemui" -> initForSystemUI()
            "com.miui.home" -> initForHome()
        }
    }

    private fun initForHome() {
        loadClass("com.miui.home.launcher.common.Utilities").methodFinder()
            .filterByName("isPadDevice").first().createHook {
                before { param ->
                    if (Thread.currentThread().stackTrace.any { it.className == "com.miui.home.recents.views.TaskViewThumbnail" }) {
                        param.result = false
                    }
                }
            }
        val clazzRecentMenuView = loadClass("com.miui.home.recents.views.RecentMenuView")
        clazzRecentMenuView.methodFinder().filterByName("getMultiWindowIconResource").first()
            .createHook {
                before {
                    val isScreenOrientationLandscape = invokeStaticMethodBestMatch(
                        loadClass("com.miui.home.launcher.DeviceConfig"),
                        "isScreenOrientationLandscape"
                    ) == true
                    it.result = if (isScreenOrientationLandscape) drawableIcTaskMultiPad
                    else drawableIcTaskMulti
                }
            }
        clazzRecentMenuView.methodFinder().filterByName("updateShowingMenuInfoIfNeed").first()
            .createHook {
                before {
                    invokeMethodBestMatch(it.thisObject, "setMultiWindowMenuIcon")
                }
            }
    }

    private fun initForSystemUI() {
        loadClass("com.android.wm.shell.common.split.SplitUtilsImpl").declaredMethods.filter { !it.isStatic }
            .createHooks {
                before {
                    it.thisObject.objectHelper().setObject("IS_CTS_MODE", true)
                }
            }
        loadClass("com.android.wm.shell.common.split.SplitUtilsImpl").methodFinder()
            .filterByName("updateConfig").first().createHook {
                before {
                    it.result = null
                    val context = it.args[0] as Context
                    val miuiSplitFeatureEnable =
                        context.resources.getBoolean(boolConfigMiuiSplitFeatureEnable)
                    val instanceSoScUtils =
                        loadClass("com.android.wm.shell.sosc.SoScUtils").classHelper()
                            .invokeStaticMethodBestMatch("getInstance")
                    val isSoScSupported = instanceSoScUtils?.objectHelper()
                        ?.invokeMethodBestMatch("isSoScSupported") == true
                    val mSplitController = getObjectOrNull(it.thisObject, "mSplitController")
                    val isLandscape = if (isSoScSupported) {
                        instanceSoScUtils?.objectHelper()
                            ?.invokeMethodBestMatch("isLandscape") == true
                    } else {
                        mSplitController?.objectHelper()?.invokeMethodBestMatch("getSplitLayout")
                            ?.objectHelper()?.invokeMethodBestMatch("isLandscape") == true
                    }

                    if (miuiSplitFeatureEnable && !isSoScSupported) {
                        if (isLandscape) {
                            Settings.System.putString(
                                context.contentResolver, "three_gesture_horizontal_ltr", "split_ltr"
                            )
                            Settings.System.putString(
                                context.contentResolver, "three_gesture_horizontal_rtl", "split_rtl"
                            )
                        } else {
                            Settings.System.putString(
                                context.contentResolver, "three_gesture_horizontal_ltr", ""
                            )
                            Settings.System.putString(
                                context.contentResolver, "three_gesture_horizontal_rtl", ""
                            )
                        }
                    }
                    setObject(it.thisObject, "mMiuiSplitFeatureEnable", miuiSplitFeatureEnable)
                    setObject(it.thisObject, "mVerticalDivisionCache", isLandscape)
                }
            }
    }
}