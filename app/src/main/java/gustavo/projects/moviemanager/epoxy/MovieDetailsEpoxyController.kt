package gustavo.projects.moviemanager.epoxy

import android.content.Context
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.databinding.*
import gustavo.projects.moviemanager.domain.models.*
import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.util.Constants.BASE_YOUTUBE_THUMBAIL_URL
import gustavo.projects.moviemanager.util.Constants.MISSING_PROFILE_PICTURE_URL
import gustavo.projects.moviemanager.util.Constants.YOUTUBE_THUMBNAIL_URL_STRING_INDEX
import gustavo.projects.moviemanager.util.DateFormatter
import gustavo.projects.moviemanager.util.RatingFormatter

class MovieDetailsEpoxyController(
        private val onVideoSelected: (String) -> Unit,
        private val onPersonSelected: (Int) -> Unit,
        private val context: Context
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
            isLoading = false
            requestModelBuild()
        }


    override fun buildModels() {

        if(isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        if(movieDetails == null) {
            DetailsFailedEpoxyModel().id("detailsFailed").addTo(this)
            return
        }


        PosterEpoxyModel(movieDetails!!.poster_path).id("poster").addTo(this)
        TitleEpoxyModel(movieDetails!!.title!!, movieDetails!!.vote_average!!).id("title").addTo(this)
        OverviewEpoxyModel(movieDetails!!.overview!!).id("overview").addTo(this)

        movieDetails!!.productionCountry.let { list ->
            if(!list.isNullOrEmpty()){
                CountryTitleEpoxyModel(list).id("countryTitle").addTo(this)

                list.forEach { country ->
                    CountryListEpoxyModel(country!!).id(country.name).addTo(this)

                }
            }
        }

        DetailsEpoxyModel(
            movieDetails!!.release_date,
            movieDetails!!.genres,
            movieDetails!!.runtime,
            context
        ).id("details").addTo(this)

        val castCarouselItems = movieDetails!!.movieCast!!.map {
            CastCarouselItemEpoxyModel(it!!, onPersonSelected).id(it!!.id)
        }

        CarouselModel_().id("cast_carousel").models(castCarouselItems).addTo(this)

        if(!movieDetails!!.movieVideos!!.isNullOrEmpty()) {
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
            movieRatingTextView.text = RatingFormatter(voteAverage)
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

    data class CountryTitleEpoxyModel(
        val countryList: List<ProductionCountry?>
    ) : ViewBindingKotlinModel<ModelMovieDetailsCountryTitleBinding>(R.layout.model_movie_details_country_title) {

        override fun ModelMovieDetailsCountryTitleBinding.bind() {
//            TODO Check plural
//            if(countryList.size > 1) {
//                countryTitle.text = "Production Countries"
//            }
        }
    }

    data class CountryListEpoxyModel(
        val productionCountry: ProductionCountry
    ) : ViewBindingKotlinModel<ModelMovieDetailsCountryItemBinding>(R.layout.model_movie_details_country_item) {

        override fun ModelMovieDetailsCountryItemBinding.bind() {
            countryNameTextView.text = productionCountry.name
        }
    }

    data class DetailsEpoxyModel(
        val releaseDate: String?,
        val listOfGenres: List<MovieGenre?>?,
        val runtime: Int?,
        val context: Context
    ) : ViewBindingKotlinModel<ModelMovieDetailsBinding>(R.layout.model_movie_details) {
        override fun ModelMovieDetailsBinding.bind() {

            if(!releaseDate.isNullOrBlank()) {
                releaseDateTextView.text = DateFormatter.formatDate(releaseDate, context)
            }else{
                releaseDateTextView.text = "N/A"
            }

            var listOfGenresText: String = "N/A"

            if(!listOfGenres.isNullOrEmpty()) {
                listOfGenresText = ""
                for ((index, value) in listOfGenres.withIndex()) {
                    if (index < listOfGenres.lastIndex) {
                        listOfGenresText += value!!.name + ", "
                    } else {
                        listOfGenresText += value!!.name + "."
                    }
                }
                genresTextView.text = listOfGenresText
            }

            var runtimeText: String = "N/A"

            if(runtime != null) {

                var runtimeHours: Int = runtime/60
                var runtimeMinutes = runtime%60

                runtimeText = if(runtimeMinutes != 0) {
                    runtimeHours.toString() + "h " + runtimeMinutes + "min"
                }else {
                    runtimeHours.toString() + "h"
                }
            }
            runtimeTextView.text = runtimeText
        }
    }

    data class CastCarouselItemEpoxyModel(
            val movieCast: MovieCast,
            private val onPersonSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelCastCarouselItemBinding>(R.layout.model_cast_carousel_item) {

        override fun ModelCastCarouselItemBinding.bind() {

            var fullImagePath = MISSING_PROFILE_PICTURE_URL

            if(movieCast.profile_path != null){
                fullImagePath = Constants.BASE_IMAGE_URL + movieCast.profile_path
            }
                Picasso.get().load(fullImagePath).into(castImageView)

            castName.text = movieCast.name
            castCharacterName.text = movieCast.character

            root.setOnClickListener {
                onPersonSelected(movieCast.id!!)
            }
        }

    }

    data class VideosTitleEpoxyModel(
            val movieVideo: List<MovieVideo?>
    ): ViewBindingKotlinModel<ModelVideosTitleBinding>(R.layout.model_videos_title) {

        override fun ModelVideosTitleBinding.bind() {

            movieVideo.let {
                if (it.size > 1) {
                    titleTextView.text = "${titleTextView.text}s"
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
                        movieVideo.key + BASE_YOUTUBE_THUMBAIL_URL.substring(YOUTUBE_THUMBNAIL_URL_STRING_INDEX,BASE_YOUTUBE_THUMBAIL_URL.length)
                fullImagePath = pathWithMovieKey
            }

            Picasso.get().load(fullImagePath).into(movieVideoThumbnail)

            root.setOnClickListener {
                onVideoSelected(movieVideo.key!!)
            }
        }
    }
}