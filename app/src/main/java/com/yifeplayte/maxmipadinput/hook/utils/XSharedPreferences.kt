package com.yifeplayte.maxmipadinput.hook.utils

import com.yifeplayte.maxmipadinput.BuildConfig
import de.robv.android.xposed.XSharedPreferences

object XSharedPreferences {
    private val prefs = XSharedPreferences(BuildConfig.APPLICATION_ID, "config")

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        reload()
        return prefs.getBoolean(key, defValue)
    }

    fun getString(key: String, defValue: String): String {
        reload()
        return prefs.getString(key, defValue) ?: defValue
    }

    fun getStringSet(key: String, defValue: Set<String>): Set<String> {
        return prefs.getStringSet(key, defValue) ?: defValue
    }

    private fun reload() {
        if (prefs.hasFileChanged()) {
            prefs.reload()
        }
    }
}