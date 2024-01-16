package com.projects.moviemanager.features.watchlist.domain

import com.projects.moviemanager.database.model.ContentEntity
import com.projects.moviemanager.database.repository.DatabaseRepository
import javax.inject.Inject
import timber.log.Timber

class WatchlistInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) {
    suspend fun getAllItems(): List<ContentEntity> {
        return databaseRepository.getAllItems()
    }
}
