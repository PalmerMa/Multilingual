# Multilingual
Multilingual Functionality (Android)

The multi-language module provides the languages currently supported by the application independent of the Android system.

**ActivityLanguageDelegate.initLanguage(applicationContext, "en-US")**


It also supports directly obtaining the string corresponding to the resource of the specified language.

**val strResId = R.string.app_name**
**val languageStr = "zh-CN"**
**LanguageUtils.getStringByTargetLanguage(applicationContext, strResId, languageStr.substring(0, 2), languageStr.substring(3))**


