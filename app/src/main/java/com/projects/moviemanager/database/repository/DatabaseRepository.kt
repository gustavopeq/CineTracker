package com.projects.moviemanager.database.repository

import com.projects.moviemanager.database.model.ItemEntity

interface DatabaseRepository {
    suspend fun insertItem(itemEntity: ItemEntity)

    suspend fun deleteItem(itemEntity: ItemEntity)

    suspend fun getAllItems(): List<ItemEntity>

    suspend fun searchItem(movieId: Int): ItemEntity?
}
