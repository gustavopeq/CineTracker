package gustavo.projects.moviemanager.epoxy

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.databinding.*
import gustavo.projects.moviemanager.domain.models.MovieCast
import gustavo.projects.moviemanager.domain.models.MovieDetails
import gustavo.projects.moviemanager.domain.models.MovieGenre
import gustavo.projects.moviemanager.domain.models.MovieVideo
import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.util.Constants.BASE_YOUTUBE_THUMBAIL_URL
import gustavo.projects.moviemanager.util.Constants.MISSING_PROFILE_PICTURE_URL
import gustavo.projects.moviemanager.util.Constants.YOUTUBE_THUMBNAIL_URL_STRING_INDEX
import gustavo.projects.moviemanager.util.DateFormatter

class MovieDetailsEpoxyController(
        private val onVideoSelected: (String) -> Unit
): EpoxyController() {

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

            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }


        PosterEpoxyModel(movieDetails!!.poster_path).id("poster").addTo(this)
        TitleEpoxyModel(movieDetails!!.title!!, movieDetails!!.vote_average!!).id("title").addTo(this)
        OverviewEpoxyModel(movieDetails!!.overview!!).id("overview").addTo(this)
        DetailsEpoxyModel(
            movieDetails!!.release_date!!,
            movieDetails!!.genres!!,
            movieDetails!!.runtime!!
        ).id("details").addTo(this)

        val castCarouselItems = movieDetails!!.movieCast!!.map {
            CastCarouselItemEpoxyModel(it!!).id(it!!.id)
        }

        CarouselModel_().id("cast_carousel").models(castCarouselItems).addTo(this)

        if(movieDetails!!.movieVideos!!.isNotEmpty()) {
            VideosTitleEpoxyModel(movieDetails!!.movieVideos!!).id("videosTitle").addTo(this)
            val videosCarouselItems = movieDetails!!.movieVideos!!.map {
                VideosCarouselItemEpoxyModel(it!!, onVideoSelected).id(it!!.id)
            }

            CarouselModel_().id("video_carousel").models(videosCarouselItems).addTo(this)
        }

    }

    data class TitleEpoxyModel(
        val movieTitle: String,
        val voteAverage: Double
    ) : ViewBindingKotlinModel<ModelMovieTitleBinding>(R.layout.model_movie_title) {
        override fun ModelMovieTitleBinding.bind() {
            titleTextView.text = movieTitle
            movieRatingTextView.text = voteAverage.toString()
        }
    }

    data class PosterEpoxyModel(
        val posterPath: String?
    ) : ViewBindingKotlinModel<ModelMoviePosterBinding>(R.layout.model_movie_poster) {
        override fun ModelMoviePosterBinding.bind() {

            var fullPosterPath = ""
            if(posterPath != null)
            {
                fullPosterPath = Constants.BASE_IMAGE_URL + posterPath
            }else
            {
                fullPosterPath = MISSING_PROFILE_PICTURE_URL
            }
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

            releaseDateTextView.text = DateFormatter().formatDate(releaseDate)

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

    data class CastCarouselItemEpoxyModel(
            val movieCast: MovieCast
    ): ViewBindingKotlinModel<ModelCastCarouselItemBinding>(R.layout.model_cast_carousel_item) {

        override fun ModelCastCarouselItemBinding.bind() {

            var fullImagePath = MISSING_PROFILE_PICTURE_URL

            if(movieCast.profile_path != null){
                fullImagePath = Constants.BASE_IMAGE_URL + movieCast.profile_path
            }
                Picasso.get().load(fullImagePath).into(castImageView)

            castName.text = movieCast.name
            castCharacterName.text = movieCast.character
        }

    }

    data class VideosTitleEpoxyModel(
            val movieVideo: List<MovieVideo?>
    ): ViewBindingKotlinModel<ModelVideosTitleBinding>(R.layout.model_videos_title) {

        override fun ModelVideosTitleBinding.bind() {

            movieVideo.let {
                if (it.size > 1) {
                    titleTextView.text = "Videos"
                }
            }
        }

    }

    data class VideosCarouselItemEpoxyModel(
            val movieVideo: MovieVideo,
            val onVideoSelected: (String) -> Unit
    ): ViewBindingKotlinModel<ModelVideosCarouselItemBinding>(R.layout.model_videos_carousel_item) {

        override fun ModelVideosCarouselItemBinding.bind() {

            var fullImagePath = MISSING_PROFILE_PICTURE_URL

            movieVideo.key.let {
                val pathWithMovieKey = BASE_YOUTUBE_THUMBAIL_URL.substring(0, YOUTUBE_THUMBNAIL_URL_STRING_INDEX) +
                        movieVideo.key + BASE_YOUTUBE_THUMBAIL_URL.substring(YOUTUBE_THUMBNAIL_URL_STRING_INDEX,Constants.BASE_YOUTUBE_THUMBAIL_URL.length)
                fullImagePath = pathWithMovieKey
            }

            Picasso.get().load(fullImagePath).into(movieVideoThumbnail)

            root.setOnClickListener {
                onVideoSelected(movieVideo.key!!)
            }
        }
    }
}