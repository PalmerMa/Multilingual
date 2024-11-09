package com.palmer.mutilingual

import android.content.Context

object ActivityLanguageDelegate {

    fun initLanguage(context: Context, defaultLanguage: String){
        LanguageUtils.currentLang = LanguageLocalManager.getLocalLanguage(context, defaultLanguage)
        LanguageUtils.setAppLanguage()
    }}