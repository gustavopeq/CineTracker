package com.projects.moviemanager.database

import com.projects.moviemanager.database.model.ItemEntity

class DatabaseRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun insertItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().insert(itemEntity)
    }

    suspend fun deleteItem(itemEntity: ItemEntity) {
        appDatabase.itemEntityDao().delete(itemEntity)
    }

    suspend fun getAllItems(): List<ItemEntity> {
        return appDatabase.itemEntityDao().getAllItems()
    }

    suspend fun searchItem(movieId: Int): ItemEntity? {
        return appDatabase.itemEntityDao().searchItem(movieId)
    }
}