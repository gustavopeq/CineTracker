package com.projects.moviemanager.features.home.domain

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.database.repository.DatabaseRepository
import com.projects.moviemanager.domain.models.content.GenericContent
import com.projects.moviemanager.domain.models.content.toGenericSearchContent
import com.projects.moviemanager.domain.models.person.PersonDetails
import com.projects.moviemanager.domain.models.person.toPersonDetails
import com.projects.moviemanager.features.watchlist.model.DefaultLists
import com.projects.moviemanager.network.models.content.movie.MovieApiResponse
import com.projects.moviemanager.network.models.content.show.ShowApiResponse
import com.projects.moviemanager.network.repository.home.HomeRepository
import com.projects.moviemanager.network.repository.movie.MovieRepository
import com.projects.moviemanager.network.repository.show.ShowRepository
import com.projects.moviemanager.network.util.Left
import com.projects.moviemanager.network.util.Right
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val homeRepository: HomeRepository,
    private val databaseRepository: DatabaseRepository,
    private val movieRepository: MovieRepository,
    private val showRepository: ShowRepository
) {
    suspend fun getTrendingMulti(): List<GenericContent> {
        val result = homeRepository.getTrendingMulti()

        var listResults: List<GenericContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getTrendingMulti failed with error: ${response.error}")
                }
                is Left -> {
                    listResults = response.value.results.mapNotNull {
                        it.toGenericSearchContent()
                    }
                }
            }
        }
        return listResults
    }

    suspend fun getAllWatchlist(): List<GenericContent> {
        val result = databaseRepository.getAllItemsByListId(
            listId = DefaultLists.WATCHLIST.listId
        )

        return result.mapNotNull { contentEntity ->
            getContentDetailsById(
                contentId = contentEntity.contentId,
                mediaType = MediaType.getType(contentEntity.mediaType)
            )
        }
    }

    private suspend fun getContentDetailsById(
        contentId: Int,
        mediaType: MediaType
    ): GenericContent? {
        val result = when (mediaType) {
            MediaType.MOVIE -> movieRepository.getMovieDetailsById(contentId)
            MediaType.SHOW -> showRepository.getShowDetailsById(contentId)
            else -> return null
        }

        var contentDetails: GenericContent? = null
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getContentDetailsById failed with error: ${response.error}")
                }
                is Left -> {
                    contentDetails = when (mediaType) {
                        MediaType.MOVIE -> {
                            (response.value as MovieApiResponse).toGenericSearchContent()
                        }
                        MediaType.SHOW -> {
                            (response.value as ShowApiResponse).toGenericSearchContent()
                        }
                        else -> return@collect
                    }
                }
            }
        }
        return contentDetails
    }

    suspend fun getTrendingPerson(): List<PersonDetails> {
        val result = homeRepository.getTrendingPerson()

        var listResults: List<PersonDetails> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getTrendingPerson failed with error: ${response.error}")
                }
                is Left -> {
                    listResults = response.value.results.map {
                        it.toPersonDetails()
                    }
                }
            }
        }
        return listResults
    }

    suspend fun getMoviesComingSoon(): List<GenericContent> {
        val (releaseDateGte, releaseDateLte) = getComingSoonDates()

        val result = homeRepository.getMoviesComingSoon(
            releaseDateGte = releaseDateGte,
            releaseDateLte = releaseDateLte
        )

        var listResults: List<GenericContent> = emptyList()
        result.collect { response ->
            when (response) {
                is Right -> {
                    Timber.e("getMoviesComingSoon failed with error: ${response.error}")
                }
                is Left -> {
                    listResults = response.value.results.mapNotNull {
                        it.toGenericSearchContent()
                    }
                }
            }
        }
        return listResults
    }

    private fun getComingSoonDates(): Pair<String, String> {
        val calendar = Calendar.getInstance()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val releaseDateGte = dateFormat.format(calendar.time)
        calendar.add(Calendar.MONTH, 1)
        val releaseDateLte = dateFormat.format(calendar.time)
        return Pair(releaseDateGte, releaseDateLte)
    }
}
