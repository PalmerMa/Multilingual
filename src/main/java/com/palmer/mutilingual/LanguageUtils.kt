package com.palmer.mutilingual

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.text.TextUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

object LanguageUtils {

    /**
     * Language of the application
     */
    var currentLang = ""

    /**
     * Get Android System Language
     */
    fun getSysLanguage(context: Context?): String? {
        if (context == null) {
            return ""
        }
        val locale = context.resources.configuration.locales[0]
        val language = locale.language
        val country = locale.country
        return "$language-$country"
    }

    /**
     * Set application language
     */
    fun setAppLanguage(context: Context, storedLocalLang: String) {
        if (currentLang.isNotEmpty() && currentLang == getCurrentAppLanguage()) {
            LogUtils.d("current language is same with app local language $currentLang")
            return
        }
        if ("default" == currentLang || TextUtils.isEmpty(currentLang)) {
            if(TextUtils.isEmpty(storedLocalLang)){
                val appLocale: LocaleListCompat = LocaleListCompat.getDefault()
                AppCompatDelegate.setApplicationLocales(appLocale)
                LogUtils.d("current language is default")
            } else {
                LogUtils.d("current language is $storedLocalLang from userprofile")
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(storedLocalLang)
                AppCompatDelegate.setApplicationLocales(appLocale)
            }
        } else {
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(currentLang)
            AppCompatDelegate.setApplicationLocales(appLocale)
            LogUtils.d("current language is $currentLang")
        }
    }

    fun getCurrentAppLanguage(): String {
        return AppCompatDelegate.getApplicationLocales().toLanguageTags()
    }

    fun setAppLanguage(languageAndroid: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(currentLang)
        AppCompatDelegate.setApplicationLocales(appLocale)
        LogUtils.d("current language is $currentLang")
    }

    //iOS:  zh-Hant, zh-Hans, en, ja
    //Android: zh-TW, zh-CN, en-US, ja-JP
    fun convertLangToIOS(langStr: String): String {
        if (TextUtils.isEmpty(langStr)) {
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

    fun convertLangToAndroid(langStr: String?): String {
        if (TextUtils.isEmpty(langStr)) {
            return "default"
        }
        return when (langStr) {
            "zh-Hant" -> "zh-TW"
            "zh-Hans" -> "zh-CN"
            "en" -> "en-US"
            "ja" -> "ja-JP"
            else -> "default"
        }
    }

    fun getStringByCurrLanguage(context: Context, strResId: Int): String {
        return if (TextUtils.isEmpty(currentLang) || currentLang == "default") {
            context.getString(strResId)
        } else {
            getStringByTargetLanguage(
                context,
                strResId,
                currentLang.substring(0, 2),
                currentLang.substring(3)
            )
        }
    }

    fun getStringByCurrLanguage(context: Context, strResId: Int, vararg args: String): String {
        return if (TextUtils.isEmpty(currentLang) || currentLang == "default") {
            context.getString(strResId, *args)
        } else {
            getStringByTargetLanguage(
                context,
                strResId,
                currentLang.substring(0, 2),
                currentLang.substring(3),
                *args
            )
        }
    }

    fun getStringArrayByCurrLanguage(context: Context, strResId: Int): Array<String> {
        return if (TextUtils.isEmpty(currentLang) || currentLang == "default") {
            context.resources.getStringArray(strResId)
        } else {
            getStringArrayByTargetLanguage(
                context,
                strResId,
                currentLang.substring(0, 2),
                currentLang.substring(3)
            )
        }
    }

    fun getStringByTargetLanguage(
        context: Context,
        stringId: Int,
        language: String,
        country: String
    ): String {
        val resources = getApplicationResource(
            context.applicationContext.packageManager,
            context.packageName, Locale(language, country)
        )
        return if (resources == null) {
            context.getString(stringId)
        } else {
            try {
                resources.getString(stringId)
            } catch (e: Exception) {
                context.getString(stringId)
            }
        }
    }

    fun getStringByTargetLanguage(
        context: Context,
        stringId: Int,
        language: String,
        country: String,
        vararg args: String
    ): String {
        val resources = getApplicationResource(
            context.packageManager, context.packageName, Locale(language, country)
        )
        return if (resources == null) {
            context.getString(stringId, *args)
        } else {
            try {
                resources.getString(stringId, *args)
            } catch (e: Exception) {
                context.getString(stringId, *args)
            }
        }
    }

    fun getStringArrayByTargetLanguage(
        context: Context,
        stringId: Int,
        language: String,
        country: String
    ): Array<String> {
        val resources = getApplicationResource(
            context.applicationContext.packageManager,
            context.packageName, Locale(language, country)
        )
        return if (resources == null) {
            context.resources.getStringArray(stringId)
        } else {
            try {
                resources.getStringArray(stringId)
            } catch (e: Exception) {
                context.resources.getStringArray(stringId)
            }
        }
    }

    private fun getApplicationResource(pm: PackageManager, pkgName: String, l: Locale): Resources? {
        var resourceForApplication: Resources? = null
        try {
            resourceForApplication = pm.getResourcesForApplication(pkgName);
            updateResource(resourceForApplication, l);
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return resourceForApplication
    }

    private fun updateResource(resource: Resources, l: Locale) {
        val config = resource.configuration
        config.locale = l
        resource.updateConfiguration(config, null)
    }


}