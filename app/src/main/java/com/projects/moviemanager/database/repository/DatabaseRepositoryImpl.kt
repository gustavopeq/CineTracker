package com.projects.moviemanager.database.repository

import com.projects.moviemanager.database.dao.ItemEntityDao
import com.projects.moviemanager.database.model.ItemEntity

class DatabaseRepositoryImpl(
    private val itemEntityDao: ItemEntityDao
) : DatabaseRepository {

    override suspend fun insertItem(itemEntity: ItemEntity) {
        itemEntityDao.insert(itemEntity)
    }

    override suspend fun deleteItem(itemEntity: ItemEntity) {
        itemEntityDao.delete(itemEntity)
    }

    override suspend fun getAllItems(): List<ItemEntity> {
        return itemEntityDao.getAllItems()
    }

    override suspend fun searchItem(movieId: Int): ItemEntity? {
        return itemEntityDao.searchItem(movieId)
    }
}
