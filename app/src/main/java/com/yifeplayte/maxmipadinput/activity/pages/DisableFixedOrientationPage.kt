package com.yifeplayte.maxmipadinput.activity.pages

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import cn.fkj233.ui.activity.MIUIActivity
import cn.fkj233.ui.activity.annotation.BMPage
import cn.fkj233.ui.activity.data.BasePage
import cn.fkj233.ui.activity.fragment.MIUIFragment
import cn.fkj233.ui.activity.view.SwitchV
import cn.fkj233.ui.activity.view.TextSummaryV
import cn.fkj233.ui.activity.view.TextSummaryWithSwitchV
import com.yifeplayte.maxmipadinput.R
import com.yifeplayte.maxmipadinput.utils.SharedPreferences.putStringSet
import io.github.ranlee1.jpinyin.PinyinFormat
import io.github.ranlee1.jpinyin.PinyinHelper
import java.util.*

@SuppressLint("NonConstantResourceId")
@BMPage(key = "DisableFixedOrientationPage", titleId = R.string.disable_fixed_orientation_scope)
class DisableFixedOrientationPage : BasePage() {
    init {
        skipLoadItem = true
    }

    override fun asyncInit(fragment: MIUIFragment) {
        fragment.showLoading()
        try {
            @Suppress("DEPRECATION") val packagesInfo = activity.packageManager.getInstalledApplications(0)
            packagesInfo.sortWith { u1, u2 ->
                return@sortWith PinyinHelper.convertToPinyinString(
                    u1.loadLabel(activity.packageManager).toString(),
                    "",
                    PinyinFormat.WITHOUT_TONE
                ).lowercase(Locale.ROOT).compareTo(
                    PinyinHelper.convertToPinyinString(
                        u2.loadLabel(activity.packageManager).toString(),
                        "",
                        PinyinFormat.WITHOUT_TONE
                    ).lowercase(Locale.ROOT)
                )
            }
            for (i in packagesInfo) {
                if ((i.flags and ApplicationInfo.FLAG_SYSTEM) != 1) fragment.addItem(
                    TextSummaryWithSwitchV(
                        TextSummaryV(
                            text = i.loadLabel(activity.packageManager).toString(),
                            tips = i.packageName
                        ), SwitchV("disable_fixed_orientation_" + i.packageName) { switchValue ->
                            @Suppress("DEPRECATION") val packagesInfo1 =
                                MIUIActivity.activity.packageManager.getInstalledApplications(0)
                            val shouldDisableFixedOrientationList = mutableListOf<String>()
                            for (j in packagesInfo1) {
                                if ((j.flags and ApplicationInfo.FLAG_SYSTEM) != 1) {
                                    if (MIUIActivity.safeSP.getBoolean(
                                            "disable_fixed_orientation_" + j.packageName,
                                            false
                                        )
                                    ) {
                                        shouldDisableFixedOrientationList.add(j.packageName)
                                    }
                                }
                            }
                            if (switchValue) {
                                if (!shouldDisableFixedOrientationList.contains(i.packageName)) {
                                    shouldDisableFixedOrientationList.add(i.packageName)
                                }
                            } else {
                                shouldDisableFixedOrientationList.remove(i.packageName)
                            }
                            MIUIActivity.safeSP.mSP?.putStringSet(
                                "should_disable_fixed_orientation_list",
                                shouldDisableFixedOrientationList.toSet()
                            )
                        }
                    )
                )
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        fragment.closeLoading()
        fragment.initData()
    }

    override fun onCreate() {}
}