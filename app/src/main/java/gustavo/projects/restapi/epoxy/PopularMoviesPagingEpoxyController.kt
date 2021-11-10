package gustavo.projects.restapi.epoxy

import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.restapi.Constants
import gustavo.projects.restapi.R
import gustavo.projects.restapi.databinding.ModelPopularMovieListItemBinding
import gustavo.projects.restapi.network.response.GetPopularMoviesByIdResponse

class PopularMoviesPagingEpoxyController: PagedListEpoxyController<GetPopularMoviesByIdResponse>() {


    override fun buildItemModel(currentPosition: Int,
                                item: GetPopularMoviesByIdResponse?
    ): EpoxyModel<*> {
        return PopularMoviesItemEpoxyModel(item!!.poster_path!!, item!!.title!!, item!!.vote_average!!).id(item.id)
    }

    data class PopularMoviesItemEpoxyModel(
            val posterPath: String,
            val movieTitle: String,
            val movieRating: Double
    ): ViewBindingKotlinModel<ModelPopularMovieListItemBinding>(R.layout.model_popular_movie_list_item) {

        override fun ModelPopularMovieListItemBinding.bind() {
            val fullPosterPath = Constants.POSTER_URL + posterPath
            Picasso.get().load(fullPosterPath).into(movieImageView)
            movieTitleTextView.text = movieTitle
            movieRatingTextView.text = movieRating.toString()
        }


    }

}