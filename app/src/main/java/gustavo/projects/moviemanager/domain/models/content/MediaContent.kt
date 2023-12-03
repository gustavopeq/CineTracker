package gustavo.projects.moviemanager.domain.models.content

import gustavo.projects.moviemanager.compose.common.MediaType
import gustavo.projects.moviemanager.network.response.content.BaseMediaContentResponse

data class MediaContent(
    override val id: Int,
    override val title: String,
    override val vote_average: Double,
    override val poster_path: String,
    override val mediaType: MediaType
) : BaseMediaContent

fun BaseMediaContentResponse.toDomain(): MediaContent {
    return MediaContent(
        id = this.id,
        title = this.title,
        vote_average = this.vote_average,
        poster_path = this.poster_path,
        mediaType = this.mediaType
    )
}