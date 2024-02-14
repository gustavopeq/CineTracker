package com.projects.moviemanager.core

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

object LanguageSupport {
    private var _userLocale = mutableStateOf(Locale.getDefault())
    val userLocale: MutableState<Locale> get() = _userLocale

    private val supportedLanguages = listOf(
        "en-US",
        "en-CA",
        "pt-BR",
        "es-ES",
        "es-MX",
        "es-BO",
        "es-CL",
        "es-CO",
        "es-EC",
        "es-PY",
        "es-PE",
        "es-PR",
        "es-UY",
        "es-VE",
        "es-CR"
    )
    private const val DEFAULT_LANGUAGE = "en-US"

    fun getLanguage(locale: Locale): String {
        val languageTag = locale.toLanguageTag()
        val language = locale.language
        return when {
            languageTag.isSupported() -> languageTag
            language == "pt" -> "pt-BR"
            language == "es" -> "es-ES"
            else -> DEFAULT_LANGUAGE
        }
    }

    private fun String.isSupported(): Boolean {
        return supportedLanguages.contains(this)
    }

    fun getUserCountryCode(): String = userLocale.value.country
}
