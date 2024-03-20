package com.yifeplayte.maxmipadinput.hook.hooks.multiple

import com.github.kyuubiran.ezxhelper.ClassUtils.loadClass
import com.github.kyuubiran.ezxhelper.EzXHelper.safeClassLoader
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHook
import com.github.kyuubiran.ezxhelper.HookFactory.`-Static`.createHooks
import com.github.kyuubiran.ezxhelper.finders.MethodFinder.`-Static`.methodFinder
import com.yifeplayte.maxmipadinput.hook.hooks.BaseMultiHook
import com.yifeplayte.maxmipadinput.hook.utils.DexKit.dexKitBridge
import com.yifeplayte.maxmipadinput.hook.utils.XSharedPreferences.getBoolean

object EnableStylusInputMethod : BaseMultiHook() {
    override val key = "enable_stylus_input_method"
    override val isEnabled
        get() = getBoolean(
            "remove_stylus_bluetooth_restriction", false
        ) && super.isEnabled
    override val hooks = mapOf(
        "android" to { hookForAndroid() },
        "com.miui.handwriting" to { hookForHandWriting() },
        "com.android.settings" to { hookForSettings() },
        "com.miui.securitycenter" to { hookForSecurityCenter() },
        "com.miui.securitycore" to { hookForSecurityCore() },
    )

    private fun hookForAndroid() {
        loadClass("com.android.server.inputmethod.StylusInputMethodSwitcher").methodFinder()
            .filterByName("isStylusDeviceConnected").single().createHook {
                returnConstant(true)
            }
    }

    private fun hookForHandWriting() {
        loadClass("android.view.InputDevice").methodFinder().filterByName("isXiaomiStylus").toList()
            .createHooks {
                returnConstant(2)
            }
    }

    private fun hookForSettings() {
        loadClass("com.android.settings.stylus.MiuiStylusSettings").methodFinder()
            .filterByName("onStylusUpdate").filterNonAbstract().single().createHook {
                before {
                    it.args[0] = true
                }
            }
    }

    private fun hookForSecurityCenter() {
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("isXiaomiStylus", "isXiaomiStylus: ")
                returnType = "boolean"
            }
        }.single().getMethodInstance(safeClassLoader).createHook {
            returnConstant(true)
        }
    }

    private fun hookForSecurityCore() {
        dexKitBridge.findMethod {
            matcher {
                usingStrings = listOf("isXiaomiStylus")
                returnType = "boolean"
            }
        }.single().getMethodInstance(safeClassLoader).createHook {
            returnConstant(true)
        }
    }
}