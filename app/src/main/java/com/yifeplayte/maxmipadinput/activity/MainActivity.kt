package com.yifeplayte.maxmipadinput.activity

import android.annotation.SuppressLint
import android.os.Bundle
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.dialog.MIUIDialog
import com.yifeplayte.maxmipadinput.R
import com.yifeplayte.maxmipadinput.util.Utils
import kotlin.system.exitProcess

class MainActivity : MIUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        checkLSPosed()
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("WorldReadableFiles")
    private fun checkLSPosed() {
        try {
            setSP(getSharedPreferences("config", MODE_WORLD_READABLE))
        } catch (exception: SecurityException) {
            isLoad = false
            MIUIDialog(this) {
                setTitle(R.string.warning)
                setMessage(R.string.not_support)
                setCancelable(false)
                setRButton(R.string.done) {
                    exitProcess(0)
                }
            }.show()
        }
    }

    init {
        initView {
            registerMain(getString(R.string.app_name), false) {
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
                Line()
                TitleText(textId = R.string.reboot)
                TextA(
                    textId = R.string.reboot_system,
                    onClickListener = {
                        MIUIDialog(this@MainActivity) {
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
    }

}