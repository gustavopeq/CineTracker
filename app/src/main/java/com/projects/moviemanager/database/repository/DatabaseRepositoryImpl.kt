package com.projects.moviemanager.database.repository

import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.database.dao.ContentEntityDao
import com.projects.moviemanager.database.model.ContentEntity

class DatabaseRepositoryImpl(
    private val contentEntityDao: ContentEntityDao
) : DatabaseRepository {

    override suspend fun insertItem(
        contentId: Int,
        mediaType: MediaType,
        listId: Int
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
        listId: Int
    ): ContentEntity? {
        val itemRemoved = contentEntityDao.getItem(
            contentId = contentId,
            mediaType = mediaType.name,
            listId = listId
        )
        if (itemRemoved != null) {
            contentEntityDao.delete(
                contentId = contentId,
                mediaType = mediaType.name,
                listId = listId
            )
        }
        return itemRemoved
    }

    override suspend fun getAllItemsByListId(listId: Int): List<ContentEntity> {
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

    override suspend fun moveItemToList(
        contentId: Int,
        mediaType: MediaType,
        currentListId: Int,
        newListId: Int
    ): ContentEntity? {
        deleteItem(
            contentId = contentId,
            mediaType = mediaType,
            listId = newListId
        )
        insertItem(
            contentId = contentId,
            mediaType = mediaType,
            listId = newListId
        )
        return deleteItem(
            contentId = contentId,
            mediaType = mediaType,
            listId = currentListId
        )
    }

    override suspend fun reinsertItem(contentEntity: ContentEntity) {
        contentEntityDao.insert(contentEntity)
    }
}
