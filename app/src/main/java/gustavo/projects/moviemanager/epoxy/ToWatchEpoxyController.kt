package gustavo.projects.moviemanager.epoxy

import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.database.model.ItemEntity
import gustavo.projects.moviemanager.databinding.ModelSearchMovieListItemBinding
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.util.Constants

class ToWatchEpoxyController(
    val onMovieSelected: (Int) -> Unit
): EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if(field){
                requestModelBuild()
            }
        }

    var itemEntityList = ArrayList<ItemEntity>()
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

        if(itemEntityList.isEmpty()) {
            //todo empty state
            return
        }

        itemEntityList.forEach {
            ToWatchListItemEpoxyModel(it.movie, onMovieSelected).id(it.dbId).addTo(this)
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

            movieRatingTextView.text = movie.vote_average.toString()

            root.setOnClickListener {
                onMovieSelected(movie.id!!)
            }
        }

    }


}