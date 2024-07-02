package com.yifeplayte.maxmipadinput.activity.pages

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.Toast
import cn.fkj233.ui.activity.MIUIActivity.Companion.safeSP
import cn.fkj233.ui.activity.annotation.BMMainPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SpinnerV
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yifeplayte.maxmipadinput.R
import com.yifeplayte.maxmipadinput.hook.PACKAGE_NAME_HOOKED
import com.yifeplayte.maxmipadinput.utils.Build.IS_HYPER_OS
import com.yifeplayte.maxmipadinput.utils.Terminal

@SuppressLint("NonConstantResourceId")
@BMMainPage(titleId = R.string.app_name)
class MainPage : BasePage() {
    override fun onCreate() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            TitleText(textId = R.string.android_r_tips)
            TextSummaryWithSwitch(
                TextSummaryV(
                    textId = R.string.remove_stylus_bluetooth_restriction,
                    tipsId = R.string.remove_stylus_bluetooth_restriction_tips
                ),
                SwitchV("remove_stylus_bluetooth_restriction", false)
            )
            return
        }

        TitleText(textId = R.string.input)
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.no_magic_pointer,
                tipsId = R.string.no_magic_pointer_tips
            ),
            SwitchV("no_magic_pointer", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.restore_esc,
                tipsId = R.string.restore_esc_tips
            ),
            SwitchV("restore_esc", false)
        )
        val bindingRemoveStylusBluetoothRestriction =
            GetDataBinding({
                safeSP.getBoolean("remove_stylus_bluetooth_restriction", false)
            }) { view, flags, data ->
                when (flags) {
                    1 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
                }
            }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_stylus_bluetooth_restriction,
                tipsId = R.string.remove_stylus_bluetooth_restriction_tips
            ),
            SwitchV(
                key = "remove_stylus_bluetooth_restriction",
                defValue = false,
                dataBindingSend = bindingRemoveStylusBluetoothRestriction.bindingSend
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) TextSummaryWithSpinner(
            TextSummaryV(
                textId = R.string.remove_stylus_bluetooth_restriction_driver_version,
                tipsId = R.string.remove_stylus_bluetooth_restriction_driver_version_tips
            ),
            SpinnerV(safeSP.getString("remove_stylus_bluetooth_restriction_driver_version", "2")) {
                add("1") {
                    safeSP.putAny("remove_stylus_bluetooth_restriction_driver_version", "1")
                }
                add("2") {
                    safeSP.putAny("remove_stylus_bluetooth_restriction_driver_version", "2")
                }
                add("3") {
                    safeSP.putAny("remove_stylus_bluetooth_restriction_driver_version", "3")
                }
            },
            dataBindingRecv = bindingRemoveStylusBluetoothRestriction.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.enable_stylus_input_method,
                tipsId = R.string.enable_stylus_input_method_tips
            ),
            SwitchV("enable_stylus_input_method", false),
            dataBindingRecv = bindingRemoveStylusBluetoothRestriction.getRecv(1)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.ignore_stylus_key_gesture,
                tipsId = R.string.ignore_stylus_key_gesture_tips
            ),
            SwitchV("ignore_stylus_key_gesture", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.set_gesture_need_finger_num_to_4,
                tipsId = R.string.set_gesture_need_finger_num_to_4_tips
            ),
            SwitchV("set_gesture_need_finger_num_to_4", false)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.fix_disable_multi_finger_gesture_filter,
                tipsId = R.string.fix_disable_multi_finger_gesture_filter_tips
            ),
            SwitchV("fix_disable_multi_finger_gesture_filter", false)
        )
        Line()
        TitleText(textId = R.string.screen)
        val bindingDisableFixedOrientation =
            GetDataBinding({
                safeSP.getBoolean("disable_fixed_orientation", false)
            }) { view, flags, data ->
                when (flags) {
                    1 -> view.visibility = if (data as Boolean) View.VISIBLE else View.GONE
                }
            }
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.disable_fixed_orientation,
                tipsId = R.string.disable_fixed_orientation_tips
            ),
            SwitchV(
                key = "disable_fixed_orientation",
                defValue = false,
                dataBindingSend = bindingDisableFixedOrientation.bindingSend
            )
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.disable_fixed_orientation_scope,
                tipsId = R.string.disable_fixed_orientation_scope_tips
            ) {
                showFragment("DisableFixedOrientationPage")
            },
            dataBindingRecv = bindingDisableFixedOrientation.getRecv(1)
        )
        if (IS_HYPER_OS) TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.vertical_split_on_portrait
            ),
            SwitchV("vertical_split_on_portrait", false)
        )
        Line()
        TitleText(textId = R.string.reboot)
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.restart_all_scope
            ) {
                MIUIDialog(activity) {
                    setTitle(R.string.warning)
                    setMessage(R.string.restart_all_scope_tips)
                    setLButton(R.string.cancel) {
                        dismiss()
                    }
                    setRButton(R.string.done) {
                        PACKAGE_NAME_HOOKED.forEach {
                            if (it != "android") Terminal.exec("killall $it")
                        }
                        Toast.makeText(
                            activity,
                            getString(R.string.finished),
                            Toast.LENGTH_SHORT
                        ).show()
                        dismiss()
                    }
                }.show()
            }
        )
        TextSummaryWithArrow(
            TextSummaryV(
                textId = R.string.reboot_system
            ) {
                MIUIDialog(activity) {
                    setTitle(R.string.warning)
                    setMessage(R.string.reboot_tips)
                    setLButton(R.string.cancel) {
                        dismiss()
                    }
                    setRButton(R.string.done) {
                        Terminal.exec("/system/bin/sync;/system/bin/svc power reboot || reboot")
                        dismiss()
                    }
                }.show()
            }
        )
    }
}