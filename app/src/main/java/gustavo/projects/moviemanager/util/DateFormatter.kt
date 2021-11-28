package gustavo.projects.moviemanager.util

class DateFormatter() {

    fun formatDate(date:String): String {
        var month: Int
        var monthFormated: String = "January"
        var day: Int
        var dayFormated: String = "1st"
        var year: String

        month = date.substring(5,7).toInt()
        day = date.substring(8,10).toInt()
        year = date.substring(0,4)

        when(month){
            1 -> monthFormated = "January"
            2 -> monthFormated = "February"
            3 -> monthFormated = "March"
            4 -> monthFormated = "April"
            5 -> monthFormated = "May"
            6 -> monthFormated = "June"
            7 -> monthFormated = "July"
            8 -> monthFormated = "August"
            9 -> monthFormated = "September"
            10 -> monthFormated = "October"
            11 -> monthFormated = "November"
            12 -> monthFormated = "December"
        }

        when(day){
            1 -> dayFormated = "1st"
            2 -> dayFormated = "2nd"
            3 -> dayFormated = "3rd"
            21 -> dayFormated = "21st"
            22 -> dayFormated = "22nd"
            23 -> dayFormated = "23rd"
            31 -> dayFormated = "31st"
            else -> dayFormated = month.toString() + "th"
        }

        return "$monthFormated $dayFormated, $year"

    }
}
