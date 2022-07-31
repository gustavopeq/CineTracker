package gustavo.projects.moviemanager.util

import java.util.Locale

class LanguageSupport {
    companion object {
        private val supportedLanguages = listOf("en-US", "pt-BR")
        private const val DEFAULT_LANGUAGE = "en-US"

        fun getLanguage(locale: Locale): String {
            val language = locale.toLanguageTag()
            return if (language.isSupported()) language else DEFAULT_LANGUAGE
        }

        private fun String.isSupported(): Boolean {
            return supportedLanguages.contains(this)
        }
    }
}