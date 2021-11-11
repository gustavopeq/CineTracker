package gustavo.projects.restapi.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.restapi.util.Constants
import gustavo.projects.restapi.R
import gustavo.projects.restapi.databinding.ModelPopularMovieListItemBinding
import gustavo.projects.restapi.domain.models.PopularMovie

class PopularMoviesPagingEpoxyController(
        private val onMovieSelected: (Int) -> Unit
): PagedListEpoxyController<PopularMovie>() {


    override fun buildItemModel(currentPosition: Int,
                                item: PopularMovie?
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
    ): ViewBindingKotlinModel<ModelPopularMovieListItemBinding>(R.layout.model_popular_movie_list_item) {

        override fun ModelPopularMovieListItemBinding.bind() {
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
            movieRatingTextView.text = movieRating.toString()

            root.setOnClickListener {
                onMovieSelected(movieId)
            }
        }
    }

}