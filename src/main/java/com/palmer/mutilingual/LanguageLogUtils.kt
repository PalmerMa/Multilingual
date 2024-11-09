package com.palmer.mutilingual

import android.text.TextUtils
import android.util.Log

object LanguageLogUtils {

    private const val TAG = "Multilingual"

    var IS_DEBUG = true

    fun d(logStr: String) {
        if (!IS_DEBUG) {
            return
        }
        if (TextUtils.isEmpty(logStr)) {
            return
        }
        Log.d(TAG, logStr)
    }
}