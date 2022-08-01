package gustavo.projects.moviemanager.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class RatingFormatter {
    companion object {
        operator fun invoke(rating: Double): String {
            if (rating == 0.0) {
                return "N/A"
            }

            var formattedRating = DecimalFormat("#.#").format(rating)
            if (formattedRating.length == 1) {
                formattedRating = "$formattedRating${DecimalFormatSymbols.getInstance().decimalSeparator}0"
            } else {
                formattedRating
            }
            return  formattedRating
        }
    }
}