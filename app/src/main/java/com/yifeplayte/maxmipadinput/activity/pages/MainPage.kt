package com.yifeplayte.maxmipadinput.activity.pages

import cn.fkj233.ui.activity.annotation.BMMainPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yifeplayte.maxmipadinput.R
import com.yifeplayte.maxmipadinput.util.Utils

@BMMainPage(titleId = R.string.app_name)
class MainPage : BasePage() {
    override fun onCreate() {
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.no_magic_pointer,
                tipsId = R.string.no_magic_pointer_tips
            ),
            SwitchV("no_magic_pointer", true)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.restore_esc,
                tipsId = R.string.restore_esc_tips
            ),
            SwitchV("restore_esc", true)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.remove_stylus_bluetooth_restriction,
                tipsId = R.string.remove_stylus_bluetooth_restriction_tips
            ),
            SwitchV("remove_stylus_bluetooth_restriction", true)
        )
        TextSummaryWithSwitch(
            TextSummaryV(
                textId = R.string.ignore_stylus_key_gesture,
                tipsId = R.string.ignore_stylus_key_gesture_tips
            ),
            SwitchV("ignore_stylus_key_gesture", true)
        )
        Line()
        TitleText(textId = R.string.reboot)
        TextSummaryArrow(
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
                        Utils.exec("/system/bin/sync;/system/bin/svc power reboot || reboot")
                    }
                }.show()
            }
        )
    }
}