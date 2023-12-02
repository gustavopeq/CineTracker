package gustavo.projects.moviemanager.util

import gustavo.projects.moviemanager.compose.common.ui.util.UiConstants.UNDEFINED_RATINGS
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Double?.formatRating(): String {
    if (this == null || this == 0.0) {
        return UNDEFINED_RATINGS
    }

    var formattedRating = DecimalFormat("#.#").format(this)
    if (formattedRating.length == 1) {
        formattedRating = "$formattedRating${DecimalFormatSymbols.getInstance().decimalSeparator}0"
    }

    return formattedRating
}
