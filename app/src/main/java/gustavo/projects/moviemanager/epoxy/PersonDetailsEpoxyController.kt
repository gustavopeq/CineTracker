package gustavo.projects.moviemanager.epoxy

import android.content.Context
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.databinding.*
import gustavo.projects.moviemanager.domain.models.Movie
import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import gustavo.projects.moviemanager.domain.models.person.PersonImages
import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.util.Constants.MISSING_PROFILE_PICTURE_URL
import gustavo.projects.moviemanager.util.DateFormatter
import gustavo.projects.moviemanager.util.RatingFormatter

class PersonDetailsEpoxyController(
    val onMovieSelected: (Int) -> Unit,
    val context: Context
): EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if(field){
                requestModelBuild()
            }
        }

    var personDetails: PersonDetails? = null
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

        if(personDetails == null) {
            DetailsFailedEpoxyModel().id("detailsFailed").addTo(this)
            return
        }


        ImageEpoxyModel(personDetails!!.profile_path).id("image").addTo(this)
        NameEpoxyModel(personDetails!!.name!!).id("personName").addTo(this)

        if(!personDetails!!.biography.isNullOrBlank()) {
            BiographyEpoxyModel(personDetails!!.biography!!).id("biography").addTo(this)
        }

        if(!personDetails!!.birthday.isNullOrBlank()) {
            BornDetailEpoxyModel(
                personDetails!!.birthday!!,
                context
            ).id("birthday").addTo(this)
        }

        if(!personDetails!!.deathday.isNullOrBlank()) {
            DeathDetailEpoxyModel(
                personDetails!!.deathday!!,
                context
            ).id("death").addTo(this)
        }

        if(!personDetails!!.place_of_birth.isNullOrBlank()) {
            BornLocationEpoxyModel(personDetails!!.place_of_birth!!).id("bornIn").addTo(this)
        }

        if(!personDetails!!.movieInList.isNullOrEmpty()) {
            MovieTitleEpoxyModel().id("movieTitle").addTo(this)

            val movieCarouselItems = personDetails!!.movieInList!!.map { movie ->
                MoviesCarouselItemEpoxyModel(movie, onMovieSelected).id(movie.id)
            }

            CarouselModel_().id("moviesCarousel").models(movieCarouselItems).addTo(this)
        }

        if(!personDetails!!.personImageList.isNullOrEmpty()) {
            ImagesTitleEpoxyModel().id("imageTitle").addTo(this)

            val imageCarouselItems = personDetails!!.personImageList!!.map { image ->
                ImageCarouselItemEpoxyModel(image!!).id(image!!.file_path)
            }

            CarouselModel_().id("imagesCarousel").models(imageCarouselItems).addTo(this)
        }


    }

    data class NameEpoxyModel(
            val personName: String
    ) : ViewBindingKotlinModel<ModelPersonTitleBinding>(R.layout.model_person_title) {
        override fun ModelPersonTitleBinding.bind() {
            nameTextView.text = personName
        }
    }

    data class ImageEpoxyModel(
        val posterPath: String?
    ) : ViewBindingKotlinModel<ModelPersonImageBinding>(R.layout.model_person_image) {
        override fun ModelPersonImageBinding.bind() {

            var fullImagePath = ""
            if(posterPath != null)
            {
                fullImagePath = Constants.BASE_IMAGE_URL + posterPath
            }else
            {
                fullImagePath = MISSING_PROFILE_PICTURE_URL
            }
            Picasso.get().load(fullImagePath).into(personImageView)
        }
    }

    data class BiographyEpoxyModel(
            val biography: String
    ) : ViewBindingKotlinModel<ModelPersonBiographyBinding>(R.layout.model_person_biography) {
        override fun ModelPersonBiographyBinding.bind() {
            biographyTextView.text = biography
        }
    }

    data class BornDetailEpoxyModel(
            val personBday: String,
            val context: Context
    ) : ViewBindingKotlinModel<ModelPersonBornBinding>(R.layout.model_person_born) {
        override fun ModelPersonBornBinding.bind() {
            bornDateTextView.text = DateFormatter.formatDate(personBday, context)
        }
    }

    data class DeathDetailEpoxyModel(
            val personDeathDate: String,
            val context: Context
    ) : ViewBindingKotlinModel<ModelPersonDeathBinding>(R.layout.model_person_death) {
        override fun ModelPersonDeathBinding.bind() {
            deathDateTextView.text = DateFormatter.formatDate(personDeathDate, context)
        }
    }

    data class BornLocationEpoxyModel(
            val bornLocation: String
    ) : ViewBindingKotlinModel<ModelPersonBornLocationBinding>(R.layout.model_person_born_location) {
        override fun ModelPersonBornLocationBinding.bind() {
            bornInTextView.text = bornLocation
        }
    }

    class MovieTitleEpoxyModel : ViewBindingKotlinModel<ModelPersonMoviesTitleBinding>(R.layout.model_person_movies_title) {

        override fun ModelPersonMoviesTitleBinding.bind() {
        }
    }

    data class MoviesCarouselItemEpoxyModel(
        val movie: Movie,
        val onMovieSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelPersonMoviesCarouselItemBinding>(R.layout.model_person_movies_carousel_item) {

        override fun ModelPersonMoviesCarouselItemBinding.bind() {

            var fullImagePath = MISSING_PROFILE_PICTURE_URL

            if(!movie.poster_path.isNullOrBlank()) {
                fullImagePath = Constants.BASE_IMAGE_URL + movie.poster_path
            }

            Picasso.get().load(fullImagePath).into(movieThumbnail)

            movieTitleTextView.text = movie.title
            movieRatingTextView.text = RatingFormatter(movie.vote_average!!)


            root.setOnClickListener {
                onMovieSelected(movie.id!!)
            }
        }
    }

    class ImagesTitleEpoxyModel : ViewBindingKotlinModel<ModelPersonImagesTitleBinding>(R.layout.model_person_images_title) {

        override fun ModelPersonImagesTitleBinding.bind() {
        }
    }

    data class ImageCarouselItemEpoxyModel(
            val image: PersonImages.Profile,
    ): ViewBindingKotlinModel<ModelPersonImagesCarouselItemBinding>(R.layout.model_person_images_carousel_item) {

        override fun ModelPersonImagesCarouselItemBinding.bind() {

            var fullImagePath = MISSING_PROFILE_PICTURE_URL

            if(!image.file_path.isNullOrBlank()) {
                fullImagePath = Constants.BASE_IMAGE_URL + image.file_path
            }

            Picasso.get().load(fullImagePath).into(personImageView)
        }
    }


}