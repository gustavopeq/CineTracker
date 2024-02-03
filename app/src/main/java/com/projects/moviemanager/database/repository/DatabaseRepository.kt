package com.projects.moviemanager.database.repository

import com.projects.moviemanager.common.domain.models.util.MediaType
import com.projects.moviemanager.database.model.ContentEntity

interface DatabaseRepository {
    suspend fun insertItem(contentId: Int, mediaType: MediaType, listId: String)

    suspend fun deleteItem(contentId: Int, mediaType: MediaType, listId: String)

    suspend fun getAllItemsByListId(listId: String): List<ContentEntity>

    suspend fun searchItems(contentId: Int, mediaType: MediaType): List<ContentEntity>

    suspend fun moveItemToList(
        contentId: Int,
        mediaType: MediaType,
        currentListId: String,
        newListId: String
    )
}
