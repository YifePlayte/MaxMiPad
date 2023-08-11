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
        runCatching {
            @Suppress("DEPRECATION") val applicationsInfo =
                activity.packageManager.getInstalledApplications(0).filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) != 1 }
                    .associateWith {
                        val label = it.loadLabel(activity.packageManager).toString()
                        PinyinHelper.convertToPinyinString(label, "", PinyinFormat.WITHOUT_TONE).lowercase()
                    }.entries.sortedBy { it.value }.map { it.key }
            for (i in applicationsInfo) {
                fragment.addItem(
                    TextSummaryWithSwitchV(TextSummaryV(
                        text = i.loadLabel(activity.packageManager).toString(), tips = i.packageName
                    ), SwitchV("disable_fixed_orientation_" + i.packageName) { switchValue ->
                        @Suppress("DEPRECATION") val packagesInfo1 = MIUIActivity.activity.packageManager.getInstalledApplications(0)
                        val shouldDisableFixedOrientationList = mutableListOf<String>()
                        for (j in packagesInfo1) {
                            if ((j.flags and ApplicationInfo.FLAG_SYSTEM) != 1) {
                                val packageName = j.packageName
                                if (MIUIActivity.safeSP.getBoolean("disable_fixed_orientation_$packageName", false)) {
                                    shouldDisableFixedOrientationList.add(packageName)
                                }
                            }
                        }
                        if (switchValue) {
                            val packageName = i.packageName
                            if (!shouldDisableFixedOrientationList.contains(packageName)) {
                                shouldDisableFixedOrientationList.add(packageName)
                            }
                        } else {
                            shouldDisableFixedOrientationList.remove(i.packageName)
                        }
                        MIUIActivity.safeSP.mSP?.putStringSet(
                            "should_disable_fixed_orientation_list", shouldDisableFixedOrientationList.toSet()
                        )
                    })
                )
            }
        }
        fragment.closeLoading()
        fragment.initData()
    }

    override fun onCreate() {}
}