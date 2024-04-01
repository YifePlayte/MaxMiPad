package com.yifeplayte.maxmipadinput.hook.utils

import com.yifeplayte.maxmipadinput.BuildConfig
import de.robv.android.xposed.XSharedPreferences

/**
 * XSharedPreferences 工具
 */
@Suppress("unused")
object XSharedPreferences {
    /**
     * XSharedPreferences 文件名
     */
    const val PREFERENCES_FILE_NAME = "config"
    private val prefs by lazy {
        XSharedPreferences(BuildConfig.APPLICATION_ID, PREFERENCES_FILE_NAME)
    }

    /**
     * 获取对应的 Boolean 属性值
     * @param key 属性名称
     * @param defValue 默认值
     */
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        prefs.reload()
        return prefs.getBoolean(key, defValue)
    }

    /**
     * 获取对应的 Int 属性值
     * @param key 属性名称
     * @param defValue 默认值
     */
    fun getInt(key: String, defValue: Int): Int {
        prefs.reload()
        return prefs.getInt(key, defValue)
    }

    /**
     * 获取对应的 String 属性值
     * @param key 属性名称
     * @param defValue 默认值
     */
    fun getString(key: String, defValue: String): String {
        prefs.reload()
        return prefs.getString(key, defValue) ?: defValue
    }

    /**
     * 获取对应的 StringSet 属性值
     * @param key 属性名称
     * @param defValue 默认值
     */
    fun getStringSet(key: String, defValue: MutableSet<String>): MutableSet<String> {
        prefs.reload()
        return prefs.getStringSet(key, defValue) ?: defValue
    }
}