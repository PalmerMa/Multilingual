package com.palmer.mutilingual

import android.text.TextUtils

object LanguageConverter {

    /**
     * Convert language tags on Android to iOS
     * Android: zh-TW, zh-CN, en-US, ja-JP
     * iOS:  zh-Hant, zh-Hans, en, ja
     */
    fun convertLangAndroidToIOS(langStr: String, defaultLanguageiOS: String): String {
        if (TextUtils.isEmpty(defaultLanguageiOS)) {
            return "default"
        }
        return when (langStr) {
            "zh-TW" -> "zh-Hant"
            "zh-CN" -> "zh-Hans"
            "en-US" -> "en"
            "ja-JP" -> "ja"
            else -> ""
        }
    }


    /**
     * Convert language tags on iOS to Android
     * Android: zh-TW, zh-CN, en-US, ja-JP
     * iOS:  zh-Hant, zh-Hans, en, ja
     */
    fun convertLangIOSToAndroid(langStr: String?, defaultLanguageAndroid: String): String {
        if (TextUtils.isEmpty(langStr)) {
            return defaultLanguageAndroid
        }
        return when (langStr) {
            "zh-Hant" -> "zh-TW"
            "zh-Hans" -> "zh-CN"
            "en" -> "en-US"
            "ja" -> "ja-JP"
            else -> "default"
        }
    }
}