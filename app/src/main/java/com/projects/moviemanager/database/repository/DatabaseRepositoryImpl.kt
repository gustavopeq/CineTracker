package com.projects.moviemanager.database.repository

import com.projects.moviemanager.common.domain.MediaType
import com.projects.moviemanager.database.dao.ContentEntityDao
import com.projects.moviemanager.database.model.ContentEntity

class DatabaseRepositoryImpl(
    private val contentEntityDao: ContentEntityDao
) : DatabaseRepository {

    override suspend fun insertItem(
        contentId: Int,
        mediaType: MediaType,
        listId: String
    ) {
        val item = ContentEntity(
            contentId = contentId,
            mediaType = mediaType.name,
            listId = listId
        )

        contentEntityDao.insert(item)
    }

    override suspend fun deleteItem(
        contentId: Int,
        mediaType: MediaType,
        listId: String
    ) {
        contentEntityDao.delete(
            contentId = contentId,
            mediaType = mediaType.name,
            listId = listId
        )
    }

    override suspend fun getAllItemsByListId(listId: String): List<ContentEntity> {
        return contentEntityDao.getAllItems(listId)
    }

    override suspend fun searchItems(
        contentId: Int,
        mediaType: MediaType
    ): List<ContentEntity> {
        return contentEntityDao.searchItems(
            contentId = contentId,
            mediaType = mediaType.name
        )
    }
}
