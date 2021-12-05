package gustavo.projects.moviemanager.epoxy

import com.airbnb.epoxy.EpoxyController
import com.squareup.picasso.Picasso
import gustavo.projects.moviemanager.R
import gustavo.projects.moviemanager.databinding.*
import gustavo.projects.moviemanager.domain.models.person.PersonDetails
import gustavo.projects.moviemanager.util.Constants
import gustavo.projects.moviemanager.util.Constants.MISSING_PROFILE_PICTURE_URL

class PersonDetailsEpoxyController: EpoxyController() {

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
        BiographyEpoxyModel(personDetails!!.biography!!).id("biography").addTo(this)

    }

    class DetailsFailedEpoxyModel():
        ViewBindingKotlinModel<ModelMovieDetailsFailedBinding>(R.layout.model_movie_details_failed) {

        override fun ModelMovieDetailsFailedBinding.bind() {

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


}