package gustavo.projects.restapi.epoxy

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.restapi.R
import gustavo.projects.restapi.databinding.*
import gustavo.projects.restapi.network.response.GetMovieByIdResponse

class MovieDetailsEpoxyController: EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if(field){
                requestModelBuild()
            }
        }

    var movieResponse: GetMovieByIdResponse? = null
        set(value) {
            field = value
            if(field != null){
                isLoading = false
                requestModelBuild()
            }
        }


    override fun buildModels() {

        if(isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if(movieResponse == null) {

            Log.d("print", "Movie not found")
            return
        }

        TitleEpoxyModel(movieResponse!!.original_title!!).id("title").addTo(this)
        PosterEpoxyModel(movieResponse!!.poster_path!!).id("poster").addTo(this)
        OverviewEpoxyModel(movieResponse!!.overview!!).id("overview").addTo(this)
        DetailsEpoxyModel(
            movieResponse!!.release_date!!,
            movieResponse!!.genres!!,
            movieResponse!!.runtime!!
        ).id("details").addTo(this)
    }

    data class TitleEpoxyModel(
        val movieTitle: String
    ) : ViewBindingKotlinModel<ModelMovieTitleBinding>(R.layout.model_movie_title) {
        override fun ModelMovieTitleBinding.bind() {
            titleTextView.text = movieTitle
        }
    }

    data class PosterEpoxyModel(
        val posterPath: String
    ) : ViewBindingKotlinModel<ModelMoviePosterBinding>(R.layout.model_movie_poster) {
        override fun ModelMoviePosterBinding.bind() {
            Picasso.get().load("https://image.tmdb.org/t/p/w500$posterPath").into(titleImageView)
        }
    }

    data class OverviewEpoxyModel(
        val overview: String
    ) : ViewBindingKotlinModel<ModelMovieOverviewBinding>(R.layout.model_movie_overview) {
        override fun ModelMovieOverviewBinding.bind() {
            overviewTextView.text = overview
        }

    }

    data class DetailsEpoxyModel(
        val releaseDate: String,
        val listOfGenres: List<GetMovieByIdResponse.Genre?>,
        val runtime: Int
    ) : ViewBindingKotlinModel<ModelMovieDetailsBinding>(R.layout.model_movie_details) {
        override fun ModelMovieDetailsBinding.bind() {
            releaseDateTextView.text = releaseDate

            var listOfGenresText: String = ""

            for((index, value) in listOfGenres!!.withIndex()){
                if(index < listOfGenres.lastIndex){
                    listOfGenresText += value!!.name + ", "
                }else{
                    listOfGenresText += value!!.name + "."
                }
            }
            genresTextView.text = listOfGenresText


            var runtimeHours: Int = runtime!!/60
            var runtimeMinutes = runtime%60
            var runtimeText: String = runtimeHours.toString() + "h "+ runtimeMinutes + "min"

            runtimeTextView.text = runtimeText
        }

    }
}