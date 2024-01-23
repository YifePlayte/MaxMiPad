package com.yifeplayte.maxmipadinput.activity.pages

import android.annotation.SuppressLint
import cn.fkj233.ui.activity.annotation.BMPage
import com.yifeplayte.maxmipadinput.R

@SuppressLint("NonConstantResourceId")
@BMPage(key = "DisableFixedOrientationPage", titleId = R.string.disable_fixed_orientation_scope)
class DisableFixedOrientationPage : BaseSelectApplicationsPage() {
    override val key = "should_disable_fixed_orientation_list"
}