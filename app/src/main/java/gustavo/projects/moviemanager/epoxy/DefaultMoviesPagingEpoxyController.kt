package gustavo.projects.moviemanager.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.databinding.ModelDefaultMovieListItemBinding
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.util.RatingFormatter

class DefaultMoviesPagingEpoxyController(
        private val onMovieSelected: (Int) -> Unit
): PagingDataEpoxyController<Movie>() {


    override fun buildItemModel(currentPosition: Int,
                                item: Movie?
    ): EpoxyModel<*> {
        return PopularMoviesItemEpoxyModel(
                item!!.id!!,
                item!!.poster_path,
                item!!.title!!,
                item!!.vote_average!!,
                onMovieSelected
                ).id(item.id)
    }

    override fun addModels(models: List<EpoxyModel<*>>) {

        if(models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        super.addModels(models)
    }

    data class PopularMoviesItemEpoxyModel(
            val movieId: Int,
            val posterPath: String?,
            val movieTitle: String,
            val movieRating: Double,
            val onMovieSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelDefaultMovieListItemBinding>(R.layout.model_default_movie_list_item) {

        override fun ModelDefaultMovieListItemBinding.bind() {
            var fullPosterPath = ""
            if(posterPath != null)
            {
                fullPosterPath = Constants.BASE_IMAGE_URL + posterPath
            }else
            {
                fullPosterPath = Constants.MISSING_PROFILE_PICTURE_URL
            }

            Picasso.get().load(fullPosterPath).into(movieImageView)
            movieTitleTextView.text = movieTitle
            movieRatingTextView.text = RatingFormatter(movieRating)

            root.setOnClickListener {
                onMovieSelected(movieId)
            }
        }
    }

}