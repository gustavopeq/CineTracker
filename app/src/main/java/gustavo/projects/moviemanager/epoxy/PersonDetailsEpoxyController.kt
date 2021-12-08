package gustavo.projects.moviemanager.epoxy

import android.util.Log
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.databinding.*
import gustavo.projects.moviemanager.domain.models.MovieCast
import gustavo.projects.moviemanager.domain.models.MovieVideo
import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.util.Constants.MISSING_PROFILE_PICTURE_URL
import gustavo.projects.moviemanager.util.DateFormatter

class PersonDetailsEpoxyController(
    val onMovieSelected: (Int) -> Unit
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
            BornDetailEpoxyModel(personDetails!!.birthday!!).id("birthday").addTo(this)
        }

        if(!personDetails!!.deathday.isNullOrBlank()) {
            DeathDetailEpoxyModel(personDetails!!.deathday!!).id("death").addTo(this)
        }

        if(!personDetails!!.place_of_birth.isNullOrBlank()) {
            BornLocationEpoxyModel(personDetails!!.place_of_birth!!).id("bornIn").addTo(this)
        }

        if(!personDetails!!.movieCast.isNullOrEmpty()) {
            MovieTitleEpoxyModel().id("movieTitle").addTo(this)

            val movieCarouselItems = personDetails!!.movieCast!!.map { movie ->
                MoviesCarouselItemEpoxyModel(movie, onMovieSelected).id(movie.id)
            }

            CarouselModel_().id("moviesCarousel").models(movieCarouselItems).addTo(this)
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
            val personBday: String
    ) : ViewBindingKotlinModel<ModelPersonBornBinding>(R.layout.model_person_born) {
        override fun ModelPersonBornBinding.bind() {
            bornDateTextView.text = DateFormatter().formatDate(personBday)
        }
    }

    data class DeathDetailEpoxyModel(
            val personDeathDate: String
    ) : ViewBindingKotlinModel<ModelPersonDeathBinding>(R.layout.model_person_death) {
        override fun ModelPersonDeathBinding.bind() {
            deathDateTextView.text = DateFormatter().formatDate(personDeathDate)
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
        val movie: MovieCast,
        val onMovieSelected: (Int) -> Unit
    ): ViewBindingKotlinModel<ModelPersonMoviesCarouselItemBinding>(R.layout.model_person_movies_carousel_item) {

        override fun ModelPersonMoviesCarouselItemBinding.bind() {

            var fullImagePath = MISSING_PROFILE_PICTURE_URL

            if(!movie.profile_path.isNullOrBlank()) {
                fullImagePath = Constants.BASE_IMAGE_URL + movie.profile_path
            }

            Picasso.get().load(fullImagePath).into(movieThumbnail)


            root.setOnClickListener {
                onMovieSelected(movie.id!!)
            }
        }
    }


}