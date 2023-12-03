package gustavo.projects.moviemanager.network.response.content

import gustavo.projects.moviemanager.compose.common.MediaType

interface BaseMediaContentResponse {
    val id: Int
    val title: String
    val vote_average: Double
    val poster_path: String
    val backdrop_path: String?
    val genre_ids: List<Int?>?
    val original_language: String?
    val original_title: String?
    val overview: String?
    val popularity: Double?
    val release_date: String?
    val vote_count: Int?
    val mediaType: MediaType
}
