package gustavo.projects.restapi.epoxy

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.restapi.Constants
import gustavo.projects.restapi.R
import gustavo.projects.restapi.databinding.*
import gustavo.projects.restapi.domain.models.Movie
import gustavo.projects.restapi.domain.models.MovieGenre

class MovieDetailsEpoxyController: EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if(field){
                requestModelBuild()
            }
        }

    var movie: Movie? = null
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

        if(movie == null) {

            Log.d("print", "Movie not found")
            return
        }

        TitleEpoxyModel(movie!!.title!!).id("title").addTo(this)
        PosterEpoxyModel(movie!!.poster_path!!).id("poster").addTo(this)
        OverviewEpoxyModel(movie!!.overview!!).id("overview").addTo(this)
        DetailsEpoxyModel(
            movie!!.release_date!!,
            movie!!.genres!!,
            movie!!.runtime!!
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