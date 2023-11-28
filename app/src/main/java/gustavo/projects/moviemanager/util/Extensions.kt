package gustavo.projects.moviemanager.util

import android.content.Context
import gustavo.projects.moviemanager.R

class Extensions {

    companion object {
        fun formatDate(date: String, context: Context): String {
            var month: Int
            var monthFormated: String = context.resources.getString(R.string.january)
            var day: Int
            var year: String

            month = date.substring(5, 7).toInt()
            day = date.substring(8, 10).toInt()
            year = date.substring(0, 4)

            when (month) {
                1 -> monthFormated = context.resources.getString(R.string.january)
                2 -> monthFormated = context.resources.getString(R.string.february)
                3 -> monthFormated = context.resources.getString(R.string.march)
                4 -> monthFormated = context.resources.getString(R.string.april)
                5 -> monthFormated = context.resources.getString(R.string.may)
                6 -> monthFormated = context.resources.getString(R.string.june)
                7 -> monthFormated = context.resources.getString(R.string.july)
                8 -> monthFormated = context.resources.getString(R.string.august)
                9 -> monthFormated = context.resources.getString(R.string.september)
                10 -> monthFormated = context.resources.getString(R.string.october)
                11 -> monthFormated = context.resources.getString(R.string.november)
                12 -> monthFormated = context.resources.getString(R.string.december)
            }

            return "$monthFormated $day, $year"
        }
    }
}
