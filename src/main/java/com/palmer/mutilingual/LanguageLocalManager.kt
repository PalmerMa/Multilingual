package com.palmer.mutilingual

import android.content.Context

object LanguageLocalManager {

    private const val SP_PALMER_LOCAL_LANGUAGE = "sp_palmer_local_language"
    private const val KEY_PALMER_LOCAL_LANGUAGE = "key_palmer_local_language"


    fun storeLocalLanguage(context: Context, localLanguage: String) {
        val sp = context.getSharedPreferences(SP_PALMER_LOCAL_LANGUAGE, Context.MODE_PRIVATE)
        sp.edit().putString(KEY_PALMER_LOCAL_LANGUAGE, localLanguage).apply()
    }

    fun getLocalLanguage(context: Context, defaultLanguage: String): String {
        val sp = context.getSharedPreferences(SP_PALMER_LOCAL_LANGUAGE, Context.MODE_PRIVATE)
        return sp.getString(KEY_PALMER_LOCAL_LANGUAGE, defaultLanguage) ?: ""
    }


}