package gustavo.projects.moviemanager.epoxy

import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.databinding.ModelEmptyToWatchListBinding
import gustavo.projects.moviemanager.databinding.ModelSearchMovieListItemBinding
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.util.RatingFormatter

class ToWatchEpoxyController(
    private val onMovieSelected: (Int) -> Unit
): EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if(field){
                requestModelBuild()
            }
        }

    var movieList = ArrayList<Movie>()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {

        if(isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if(movieList.isEmpty()) {
            EmptyToWatchListEpoxyModel().id("empty").addTo(this)
            return
        }

        movieList.forEach {
            ToWatchListItemEpoxyModel(it, onMovieSelected).id(it.id).addTo(this)
        }

    }

    data class ToWatchListItemEpoxyModel(
        val movie: Movie,
        val onMovieSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelSearchMovieListItemBinding>(R.layout.model_search_movie_list_item) {

        override fun ModelSearchMovieListItemBinding.bind() {
            movieTitleTextView.text = movie.title
            var fullPosterPath = ""
            if(movie.poster_path != null)
            {
                fullPosterPath = Constants.BASE_IMAGE_URL + movie.poster_path
            }else
            {
                fullPosterPath = Constants.MISSING_PROFILE_PICTURE_URL
            }
            Picasso.get().load(fullPosterPath).into(movieImageView)

            movieRatingTextView.text = RatingFormatter(movie.vote_average!!)

            root.setOnClickListener {
                onMovieSelected(movie.id!!)
            }
        }

    }

    class EmptyToWatchListEpoxyModel : ViewBindingKotlinModel<ModelEmptyToWatchListBinding>(R.layout.model_empty_to_watch_list) {

        override fun ModelEmptyToWatchListBinding.bind() {
            // Nothing to do
        }


    }


}