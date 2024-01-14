package com.projects.moviemanager.util

import java.util.Locale

class LanguageSupport {
    companion object {
        private val supportedLanguages = listOf(
            "en-US",
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
            "es-CR")
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
    }
}