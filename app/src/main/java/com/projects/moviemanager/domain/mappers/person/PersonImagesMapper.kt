package com.projects.moviemanager.domain.mappers.person

import com.projects.moviemanager.domain.models.person.PersonImages
import com.projects.moviemanager.network.response.person.GetPersonImagesByIdResponse

object PersonImagesMapper {

    fun buildFrom(response: GetPersonImagesByIdResponse) : PersonImages {

        val personImagesList = mutableListOf<PersonImages.Profile>()

        response.profiles!!.map{ image ->
            personImagesList.add(PersonImages.Profile(image!!.aspect_ratio, image!!.file_path, image!!.height, image!!.width))
        }


        return PersonImages(response.id, personImagesList)
    }
}