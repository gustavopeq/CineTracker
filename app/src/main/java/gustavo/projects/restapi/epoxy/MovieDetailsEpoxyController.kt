package gustavo.projects.restapi.epoxy

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.restapi.Constants
import gustavo.projects.restapi.R
import gustavo.projects.restapi.databinding.*
import gustavo.projects.restapi.domain.models.MovieCast
import gustavo.projects.restapi.domain.models.MovieDetails
import gustavo.projects.restapi.domain.models.MovieGenre

class MovieDetailsEpoxyController: EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if(field){
                requestModelBuild()
            }
        }

    var movieDetails: MovieDetails? = null
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

        if(movieDetails == null) {

            Log.d("print", "Movie not found")
            return
        }

        TitleEpoxyModel(movieDetails!!.title!!).id("title").addTo(this)
        PosterEpoxyModel(movieDetails!!.poster_path!!).id("poster").addTo(this)
        OverviewEpoxyModel(movieDetails!!.overview!!).id("overview").addTo(this)
        DetailsEpoxyModel(
            movieDetails!!.release_date!!,
            movieDetails!!.genres!!,
            movieDetails!!.runtime!!
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
            val fullPosterPath = Constants.POSTER_URL + posterPath
            Picasso.get().load(fullPosterPath).into(titleImageView)
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
        val listOfGenres: List<MovieGenre?>,
        val runtime: Int
    ) : ViewBindingKotlinModel<ModelMovieDetailsBinding>(R.layout.model_movie_details) {
        override fun ModelMovieDetailsBinding.bind() {
            releaseDateTextView.text = releaseDate

            var listOfGenresText: String = ""

            for((index, value) in listOfGenres.withIndex()){
                if(index < listOfGenres.lastIndex){
                    listOfGenresText += value!!.name + ", "
                }else{
                    listOfGenresText += value!!.name + "."
                }
            }
            genresTextView.text = listOfGenresText


            var runtimeHours: Int = runtime/60
            var runtimeMinutes = runtime%60
            var runtimeText: String = runtimeHours.toString() + "h "+ runtimeMinutes + "min"

            runtimeTextView.text = runtimeText
        }

    }
}